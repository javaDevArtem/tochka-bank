package com.tochka.bank.user;

import com.tochka.bank.account.Account;
import com.tochka.bank.account.AccountService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private AccountService accountService;

    private SessionFactory sessionFactory;

    public UserService(AccountService accountService, SessionFactory sessionFactory) {
        this.accountService = accountService;
        this.sessionFactory = sessionFactory;
    }

    public User createUser(String login) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            User existedUser = session.createQuery("FROM User WHERE login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResultOrNull();
            if (existedUser != null) {
                throw new IllegalArgumentException("User with this login = %s is already exist".formatted(login));
            }
            User user = new User(null, login, new ArrayList<>());
            session.persist(user);
            accountService.createAccount(user);
            transaction.commit();
            return user;

        }
    }

    public Optional<User> findUserById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            return Optional.of(user);
        }
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        }
    }
}
