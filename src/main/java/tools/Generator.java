package tools;

import models.Gender;
import models.Person;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Generator {

    private static final Logger logger = Logger.getLogger(Generator.class);
    private static final int maxAge = 100;
    private static final int maxTaxNumber = 99999999;

    static public List<Person> generate(int count) {

        List<Person> people = new LinkedList();

        List<String> maleNames;
        List<String> femaleNames;
        List<String> surNames;
        List<String> regions;
        try {
            maleNames = loadData("src/main/resources/dictionaries/MaleNames.txt");
            femaleNames = loadData("src/main/resources/dictionaries/FemaleNames.txt");
            surNames = loadData("src/main/resources/dictionaries/Surnames.txt");
            regions = loadData("src/main/resources/dictionaries/Regions.txt");

            for (int i = 0; i < count; i++) {
                Gender gender = Gender.values()[randomGenerate(0, 2)];
                String name;
                if (gender == Gender.MALE) {
                    name = maleNames.get(randomGenerate(0, maleNames.size()));
                } else {
                    name = femaleNames.get(randomGenerate(0, femaleNames.size()));
                }
                String surName = surNames.get(randomGenerate(0, surNames.size()));
                String region = regions.get(randomGenerate(0, regions.size()));
                int taxNumber = randomGenerate(0, maxTaxNumber);
                int age = randomGenerate(0, maxAge);
                people.add(Person.personFactory(name, surName, gender, age, region, taxNumber));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error(ex);
        }
        return people;
    }

    static private List<String> loadData(String path) throws IOException {
        List<String> data = null;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try {
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);

            data = Files.readAllLines(Paths.get(path), Charset.defaultCharset());

            logger.info(path + " successfully loaded");
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error(ex);
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileReader != null) {
                fileReader.close();
            }
        }
        return data;
    }

    static private int randomGenerate(int min, int max) {
        return min + (int) (Math.random() * (max - min));
    }
}
