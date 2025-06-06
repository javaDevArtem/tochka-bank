package com.tochka.bank.account;

import com.tochka.bank.hibernate.TransactionHelper;
import com.tochka.bank.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountProperties accountProperties;
    private final SessionFactory sessionFactory;
    private final TransactionHelper transactionHelper;


    public AccountService(AccountProperties accountProperties, SessionFactory sessionFactory, TransactionHelper transactionHelper) {
        this.accountProperties = accountProperties;
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }

    public Account createAccount(User user) {
        return transactionHelper.executeInTransaction(() -> {
            Session session = sessionFactory.getCurrentSession();
            Account account = new Account(null, user, accountProperties.getDefaultAccountAmount());
            session.persist(account);
            return account;
        });
    }

    private Optional<Account> findAccountById(Long id) {
        Account account = sessionFactory.getCurrentSession()
                .get(Account.class, id);
        return Optional.ofNullable(account);
    }

    public void depositAccount(Long accountId, int moneyToDeposit) {
        transactionHelper.executeInTransaction(() -> {
            Account account = findAccountById(accountId).orElseThrow(() -> new IllegalArgumentException("No such account=%s".formatted(accountId)));

            if (moneyToDeposit <= 0) {
                throw new IllegalArgumentException("Cannot deposit not positive amount");
            }

            account.setMoneyAmount(account.getMoneyAmount() + moneyToDeposit);
            return 0;
        });
    }

    public void withdrawFromAccount(Long accountId, int amountToWithdraw) {
        transactionHelper.executeInTransaction(() -> {
            if (amountToWithdraw <= 0) {
                throw new IllegalArgumentException("Cannot withdraw not positive amount");
            }

            Account account = findAccountById(accountId).orElseThrow(() -> new IllegalArgumentException("No such account=%s"
                    .formatted(accountId)));

            if (amountToWithdraw > account.getMoneyAmount()) {
                throw new IllegalArgumentException("Cannot withdraw from account: %s, moneyAmount= %s, attemptedWithdraw= %s "
                        .formatted(accountId, account.getMoneyAmount(), amountToWithdraw));
            }

            account.setMoneyAmount(account.getMoneyAmount() - amountToWithdraw);
            return 0;
        });

    }

    public Account closeAccount(Long accountId) {
        return transactionHelper.executeInTransaction(() -> {
            Account accountToRemove = findAccountById(accountId)
                    .orElseThrow(() -> new IllegalArgumentException("No such user with id=%s".formatted(accountId)));
            List<Account> accountList = accountToRemove.getUser().getAccountList();

            if (accountList.size() == 1) {
                throw new IllegalArgumentException("Cannot close the only one account");
            }

            Account accountToDeposit = accountList.stream()
                    .filter(acc -> !Objects.equals(acc.getId(), accountId))
                    .findFirst()
                    .orElseThrow();

            accountToDeposit.setMoneyAmount(accountToDeposit.getMoneyAmount() + accountToRemove.getMoneyAmount());
            sessionFactory.getCurrentSession().remove(accountToRemove);
            return accountToRemove;
        });

    }

    public void transfer(Long fromAccountId, Long toAccountId, int amountToTransfer) {
        transactionHelper.executeInTransaction(() -> {
            if (amountToTransfer <= 0) {
                throw new IllegalArgumentException("Cannot transfer not positive amount");
            }

            Account accountFrom = findAccountById(fromAccountId)
                    .orElseThrow(() -> new IllegalArgumentException("No such user with id=%s".formatted(fromAccountId)));
            Account accountTo = findAccountById(toAccountId)
                    .orElseThrow(() -> new IllegalArgumentException("No such user with id=%s".formatted(toAccountId)));

            if (accountFrom.getMoneyAmount() < amountToTransfer) {
                throw new IllegalArgumentException("Cannot transfer from account: %s, moneyAmount= %s, attemptedTransfer= %s "
                        .formatted(accountFrom, accountFrom.getMoneyAmount(), amountToTransfer));
            }

            int totalAmountToDeposit = !Objects.equals(accountTo.getUser().getId(), accountFrom.getUser().getId())
                    ? (int) (amountToTransfer * (1 - accountProperties.getTransferCommission()))
                    : amountToTransfer;

            accountFrom.setMoneyAmount(accountFrom.getMoneyAmount() - amountToTransfer);
            accountTo.setMoneyAmount(accountTo.getMoneyAmount() + totalAmountToDeposit);
            return 0;
        });
    }
}
