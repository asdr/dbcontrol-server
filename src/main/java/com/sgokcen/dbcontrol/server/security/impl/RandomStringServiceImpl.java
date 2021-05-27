package com.sgokcen.dbcontrol.server.security.impl;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import com.sgokcen.dbcontrol.server.service.RandomStringService;
import org.springframework.stereotype.Service;

/**
 * 
 * This class has been taken from
 * https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string.
 * 
 **/
@Service
public class RandomStringServiceImpl implements RandomStringService {

    /**
     * Generate a random string.
     */
    public String next(boolean withTimestamp) {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        String str = new String(buf);

        if (withTimestamp) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-hh:mm:ss", Locale.US);
            String timestamp = sdf.format(new Date());
            return str + "|" + timestamp;
        }

        return str;
    }

    @Override
    public String next() {
        return next(false);
    }

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase(Locale.ROOT);

    public static final String digits = "0123456789";

    public static final String alphanum = upper + lower + digits;

    private final Random random;

    private final char[] symbols;

    private final char[] buf;

    public RandomStringServiceImpl(int length, Random random, String symbols) {
        if (length < 1)
            throw new IllegalArgumentException();
        if (symbols.length() < 2)
            throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    /**
     * Create an alphanumeric string generator.
     */
    public RandomStringServiceImpl(int length, Random random) {
        this(length, random, alphanum);
    }

    /**
     * Create an alphanumeric strings from a secure generator.
     */
    public RandomStringServiceImpl(int length) {
        this(length, new SecureRandom());
    }

    /**
     * Create session identifiers.
     */
    public RandomStringServiceImpl() {
        this(21);
    }

}
