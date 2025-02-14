package com.hackathon.finservice.Service;

import com.hackathon.finservice.DTO.TransactionResponse;
import com.hackathon.finservice.Entities.*;
import com.hackathon.finservice.Repositories.AccountRepository;
import com.hackathon.finservice.Repositories.TransactionRepository;
import com.hackathon.finservice.Repositories.TransactionStatusRepository;
import com.hackathon.finservice.Repositories.TransactionTypeRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {


    private final TransactionRepository transactionRepository;
    private final TransactionStatusRepository transactionStatusRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final AccountRepository accountRepository;
    private static final Logger logger= LoggerFactory.getLogger(TransactionService.class);

    @Transactional
    public void deposit(Double amount, User user) {

        if (amount == null || amount <= 0) {
            logger.error("Invalid deposit amount: {}. Amount must be a positive number.", amount);
            throw new IllegalArgumentException("Amount must be a positive number.");
        }

        logger.info("Initiating deposit of {} for user with ID: {}", amount, user.getId());
        Account mainAccount = getMainAccount(user);
        if (mainAccount == null) {
            throw new RuntimeException("Main account not found");
        }

        double balanceBefore = mainAccount.getBalance();

        TransactionType transactionType = new TransactionType();
        transactionType.setType(TransactionType.TransactionTypeEnum.CASH_DEPOSIT);

        TransactionStatus transactionStatus = new TransactionStatus();//por defecto pending

        double commission = amount > 50000 ? amount * 0.02 : 0.0;//paso la aplicacion de la comision en el service

        transactionTypeRepository.save(transactionType);
        transactionStatusRepository.save(transactionStatus);

        logger.debug("Transaction type and status saved for deposit. Type: {}, Status: {}", transactionType.getType(), transactionStatus.getStatus());

        Transaction transaction = new Transaction();
        transaction.setTransactionStatus(transactionStatus);
        transaction.setTransactionType(transactionType);
        transaction.setSenderAccount(null);//sin cuenta de origen
        transaction.setReceiverAccount(mainAccount);//deposito a la cuenta main del user
        transaction.setAmount(amount);//establezco la cantidad, el tiempo por @CreationTimestamp
        transaction.setEffectiveAmount(amount - commission);//
        transaction.setAccount(mainAccount);//cuenta vinculada al usuario que realiza la accion
        transaction.setBalanceBefore(balanceBefore);
        transaction.setBalanceAfter(balanceBefore + (amount - commission));
        transactionRepository.save(transaction);

        logger.info("Transaction saved for user with ID: {}. Amount deposited: {}", user.getId(), amount);

        mainAccount.setBalance((mainAccount.getBalance()+amount)-commission);//sumo el doposito a la cuenta main
        accountRepository.save(mainAccount);

        logger.info("Updated balance for user with ID: {}. New balance: {}", user.getId(), mainAccount.getBalance());

    }

    private Account getMainAccount(User user) {

        return user.getAccounts().stream()
                .filter(account -> "Main".equals(account.getAccountType()))
                .findFirst()
                .orElse(null);
    }

    @Transactional
    public void withdraw(Double amount, User user) {

        Account mainAccount = getMainAccount(user);

        double balanceBefore = mainAccount.getBalance();

        double amountWithComision = amount;

        if (mainAccount.getBalance() < amountWithComision){
            throw new IllegalArgumentException("Insufficient balance");
        }

        if (amount > 10000){
            amountWithComision = amount * 0.01 + amount;
        }

        TransactionType transactionType = new TransactionType();
        TransactionStatus transactionStatus = new TransactionStatus();//por defecto pending

        transactionType.setType(TransactionType.TransactionTypeEnum.CASH_WITHDRAWAL);//type retirada

        transactionTypeRepository.save(transactionType);
        transactionStatusRepository.save(transactionStatus);

        logger.debug("Transaction type and status saved for withdraw. Type: {}, Status: {}", transactionType.getType(), transactionStatus.getStatus());

        mainAccount.setBalance(mainAccount.getBalance() - amountWithComision);
        accountRepository.save(mainAccount);

        logger.info("Updated balance for user with ID: {}. New balance: {}. Se ha restado: {}", user.getId(), mainAccount.getBalance(),amount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setEffectiveAmount(amountWithComision);
        transaction.setBalanceBefore(balanceBefore);//balance antes de la transaccion
        transaction.setBalanceAfter(balanceBefore-amountWithComision);
        transaction.setTransactionType(transactionType);
        transaction.setTransactionStatus(transactionStatus);
        transaction.setReceiverAccount(null);
        transaction.setSenderAccount(null);
        transaction.setAccount(mainAccount); // vinculacion al user
        transactionRepository.save(transaction);

        logger.info("Transaction saved for user with ID: {}. Amount withdrawn: {}", user.getId(), amount);
    }

    @Transactional
    public void transferFunds(Double amount, UUID targetAccountNumber, User user) {

        Account senderAccount = getMainAccount(user);
        if (senderAccount.getBalance()<amount){
            throw new IllegalArgumentException("Insufficient balance");
        }

        if (amount == null || amount <= 0) {
            logger.error("Invalid transfer amount: {}. Amount must be a positive number.", amount);
            throw new IllegalArgumentException("Amount must be a positive number.");
        }

        Account targetAccount = accountRepository.findByAccountNumber(targetAccountNumber);// Convertir a UUID
        if (targetAccount == null) {
            throw new IllegalArgumentException("Target account not found.");
        }

        double balanceBefore = senderAccount.getBalance();

        senderAccount.setBalance(balanceBefore - amount);
        targetAccount.setBalance(targetAccount.getBalance() + amount);

        accountRepository.save(senderAccount);
        accountRepository.save(targetAccount);

        if ("Invest".equals(targetAccount.getAccountType())) {
            double newBalance = targetAccount.getBalance() + (targetAccount.getBalance() * 0.10);
            targetAccount.setBalance(newBalance);
            accountRepository.save(targetAccount);

            logger.info("Interest applied to Invest account {}. New balance: {}", targetAccount.getAccountNumber(), newBalance);
        }

        TransactionType transferType = transactionTypeRepository.save(new TransactionType(TransactionType.TransactionTypeEnum.CASH_TRANSFER));
        TransactionStatus pendingStatus = new TransactionStatus();

        if (amount >= 80000){//reasigna a fraudulento si la cantidad es superior a 80.000
            pendingStatus.setStatus(TransactionStatus.Status.FRAUD);
        }
        transactionStatusRepository.save(pendingStatus);//guarrdo status una vez verificado.

        Transaction transaction = new Transaction(
                senderAccount,
                targetAccount,
                transferType,
                pendingStatus,
                amount,
                amount,
                balanceBefore,
                balanceBefore-amount

        );
        transaction.setAccount(senderAccount);//es el main account, relaccion con user
        transactionRepository.save(transaction);

        logger.info("Fund transfer completed. Amount: {}. Sender: {}. Target: {}", amount, senderAccount.getAccountNumber(), targetAccount.getAccountNumber());

        List<Transaction> recentTransfers = transactionRepository.findTop5BySenderAccountOrderByTimestampDesc(senderAccount);

        if (recentTransfers.size() >=5) {
            Transaction firstTransaction = recentTransfers.get(0);
            Transaction lastTransaction = recentTransfers.get(recentTransfers.size() - 1);

            long timeDifference = firstTransaction.getTimestamp().getTime() - lastTransaction.getTimestamp().getTime();

            if (timeDifference < 5000) {
                logger.warn("Fraud detected: multiple transfers within 5 seconds. Transactions affected:");

                // Marcar todas las transacciones involucradas como fraudulentas
                for (Transaction recentTransaction : recentTransfers) {

                    updateTransactionStatus(recentTransaction,TransactionStatus.Status.FRAUD);
                    logger.info("Transaction ID: {} marked as fraudulent.", recentTransaction.getId());
                }
            }
        }
    }

    public List<TransactionResponse> getTransactionHistory(User user) {

        List<Transaction> transactions = transactionRepository.findByAccount_User(user);

        Collections.reverse(transactions);

        return transactions.stream().map(transaction -> {
            TransactionResponse response = new TransactionResponse();
            response.setId(transaction.getId());
            response.setAmount(transaction.getAmount());
            response.setTransactionType(transaction.getTransactionType().getType().name());
            response.setTransactionStatus(transaction.getTransactionStatus().getStatus().name());
            response.setTransactionDate(transaction.getTimestamp());
            response.setSourceAccountNumber(transaction.getAccount().getAccountNumber());

            response.setTargetAccountNumber(
                    transaction.getSenderAccount() != null
                            ? transaction.getReceiverAccount().getAccountNumber().toString()
                            : "N/A"
            );

            return response;
        }).collect(Collectors.toList());
    }

    private void updateTransactionStatus(Transaction transaction, TransactionStatus.Status status) {
        TransactionStatus transactionStatus = new TransactionStatus(status);
        transaction.setTransactionStatus(transactionStatusRepository.save(transactionStatus));
        transactionRepository.save(transaction);
    }

}
