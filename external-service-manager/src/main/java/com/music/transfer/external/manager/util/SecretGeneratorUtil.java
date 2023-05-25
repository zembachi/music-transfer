package com.music.transfer.external.manager.util;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.security.SecureRandom;
import java.util.Random;

public class SecretGeneratorUtil {

    private SecretGeneratorUtil() {

    }

    public static String generate() {
        PasswordGenerator generator = new PasswordGenerator();
        Random random = new SecureRandom();
        CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase, 5);
        CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase, 5);
        CharacterRule digitalRule = new CharacterRule(EnglishCharacterData.Digit, 5);
        CharacterRule specialCharRule = new CharacterRule(new SpecialCharacterDataRule(), 2);
        return generator.generatePassword(random.nextInt(43, 129), lowerCaseRule, upperCaseRule,
                digitalRule, specialCharRule);
    }

}
