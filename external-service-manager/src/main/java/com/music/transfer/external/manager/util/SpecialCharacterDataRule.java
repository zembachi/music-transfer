package com.music.transfer.external.manager.util;

import org.passay.CharacterData;

public class SpecialCharacterDataRule implements CharacterData {

    private static final String ERROR_CODE = "Spec char error";

    @Override
    public String getErrorCode() {
        return ERROR_CODE;
    }

    @Override
    public String getCharacters() {
        return "~._-";
    }

}
