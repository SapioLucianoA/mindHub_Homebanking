package com.mindhub.homebanking.models;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CardsService {
    public  String generateCVV() {
        Random rand = new Random();
        int num = rand.nextInt(999) + 1;
        return String.format("%03d", num);
    }

    public  String generateCardNumber() {
        Random rand = new Random();
        int numA = rand.nextInt(9999) + 1; // Esto genera un número aleatorio entre 100 y 999.

        int numB = rand.nextInt(9999) + 1;

        int numC = rand.nextInt(9999) + 1;

        int numD = rand.nextInt(9999) + 1;

        String num1 = String.format("%04d", numA);
        String num2 = String.format("%04d", numB);
        String num3 = String.format("%04d", numC);
        String num4 = String.format("%04d", numD);
        return num1 + "-" + num2 + "-" + num3 + "-" + num4;
    }
}
