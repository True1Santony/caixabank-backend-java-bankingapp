package com.hackathon.finservice.Service;

import com.hackathon.finservice.Entities.Account;
import com.hackathon.finservice.Entities.Transaction;
import com.hackathon.finservice.Entities.TransactionStatus;
import com.hackathon.finservice.Entities.TransactionType;
import com.hackathon.finservice.Repositories.AccountRepository;
import com.hackathon.finservice.Repositories.TransactionRepository;
import com.hackathon.finservice.Repositories.TransactionStatusRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TransactionMonitorService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionMonitorService.class);

    private final TransactionRepository transactionRepository;
    private final TransactionStatusRepository transactionStatusRepository;
    private final AccountRepository accountRepository;

    private final UserService userService;

    @Scheduled(fixedDelay = 20, timeUnit = TimeUnit.SECONDS)
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void monitorTransactions() {
        logger.info("Starting transaction monitoring...");

        List<Transaction> pendingTransactions = transactionRepository.findAllByTransactionStatus_Status(TransactionStatus.Status.PENDING);

        for (Transaction transaction : pendingTransactions) {

            processTransaction(transaction);
        }

        logger.info("Transaction monitoring completed.");
    }

    private void processTransaction(Transaction transaction) {
        try {
            switch (transaction.getTransactionType().getType()) {
                case CASH_DEPOSIT:
                    processDepositTransaction(transaction);
                    break;
                case CASH_WITHDRAWAL:
                    processWithdrawalTransaction(transaction);
                    break;
                case CASH_TRANSFER:
                    processTransferTransaction(transaction);
                    break;
                default:
            }
        } catch (Exception e) {
            logger.error("Error processing transaction ID: {}", transaction.getId(), e);
        }
    }

    private void processDepositTransaction(Transaction transaction) {
        double commission = transaction.getAmount() > 50000 ? transaction.getAmount() * 0.02 : 0.0;
        if(commission == (transaction.getAmount()-transaction.getEffectiveAmount()) && (transaction.getBalanceAfter()-transaction.getBalanceBefore()) == transaction.getEffectiveAmount()){
            updateTransactionStatus(transaction, TransactionStatus.Status.APPROVED);
            logger.info("Deposit processed. ID: {}. Commission: {}. Final amount: {}", transaction.getId(), commission, transaction.getAmount());
        }else{
            logger.info("Deposit NOT processed. ID: {}. Commission: {}. Final amount: {}", transaction.getId(), commission, transaction.getAmount());
        }

    }

    private void processWithdrawalTransaction(Transaction transaction) {
        double commission = transaction.getAmount() > 10000 ? transaction.getAmount() * 0.01 : 0.0;

        if (commission == (transaction.getEffectiveAmount()-transaction.getAmount()) && transaction.getEffectiveAmount() == (transaction.getBalanceBefore() - transaction.getBalanceAfter())) {
            updateTransactionStatus(transaction, TransactionStatus.Status.APPROVED);
            logger.info("Withdrawal processed. ID: {}. Commission: {}. Amount withdrawn: {}", transaction.getId(), commission, transaction.getAmount());
        } else {
            logger.info("Withdrawal NOT processed. ID: {}. Commission: {}. Amount withdrawn: {}", transaction.getId(), commission, transaction.getAmount());
        }

    }

    private void processTransferTransaction(Transaction transaction) {

        if (transaction.getBalanceBefore() - transaction.getBalanceAfter() == transaction.getAmount()) {
            updateTransactionStatus(transaction, TransactionStatus.Status.APPROVED);
            logger.info("Transfer processed. ID: {}. Amount withdrawn: {}", transaction.getId(), transaction.getAmount());
        } else {
            logger.info("Transfer NOT processed. ID: {}. Amount withdrawn: {}", transaction.getId(), transaction.getAmount());
        }
    }

    private void updateTransactionStatus(Transaction transaction, TransactionStatus.Status status) {
        TransactionStatus transactionStatus = transactionStatusRepository.save(new TransactionStatus(status));
        transaction.setTransactionStatus(transactionStatus);
        transaction.setTimestamp(new Date());
        transactionRepository.save(transaction);
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void applyInterestToInvestmentAccounts() {
        logger.info("Applying interest to investment accounts...");

        List<Account> investmentAccounts = accountRepository.findAllByAccountTypeAndBalanceGreaterThan("Invest", 0);

        for (Account account : investmentAccounts) {
            double previousBalance = account.getBalance();
            double newBalance = previousBalance * 1.10; // Aplicar el 10% de interés
            account.setBalance(newBalance);
            accountRepository.save(account);

            logger.info("Interest applied to account {}: previous balance = {}, new balance = {}",
                    account.getAccountNumber(), previousBalance, newBalance);

            //registro de transacción para esta operación
            Transaction interestTransaction = new Transaction();
            interestTransaction.setAccount(userService.getMainAccount(account.getUser()));
            interestTransaction.setReceiverAccount(account);//cuenta que recibe el interes
            interestTransaction.setTransactionType(new TransactionType(TransactionType.TransactionTypeEnum.CASH_DEPOSIT));
            interestTransaction.setAmount(newBalance - previousBalance);
            interestTransaction.setBalanceBefore(previousBalance);
            interestTransaction.setBalanceAfter(newBalance);
            interestTransaction.setTimestamp(new Date());
            interestTransaction.setTransactionStatus(new TransactionStatus(TransactionStatus.Status.APPROVED));
            transactionRepository.save(interestTransaction);
        }

        logger.info("Interest application completed.");
    }
}