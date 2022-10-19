import java.io.*;
import java.io.FileInputStream;
import java.io.InputStream;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.lang.Math;
import java.util.*;

/**
 * Author: Tiffany Wilson
 * Version 1.0
 * Date: 10/12/22
 */

public class driver  {

    public static void main(String[] args) throws IOException, CsvValidationException{
        StringBuilder character = new StringBuilder();
        StringBuilder name = new StringBuilder();
        ArrayList<Actor> actorList = new ArrayList<>();

        InputStream stream = new FileInputStream(args[0]);
        CSVReader file = new CSVReaderBuilder( new InputStreamReader(stream, StandardCharsets.UTF_8)).withSkipLines(1).build();
            String [] values = null;
            int colonCount = 0;
            while((values = file.readNext()) != null) {
                String castList = values[2];

                for (int i = 0; i < castList.length(); i++) {

                    if (castList.charAt(i) == ':') {
                        colonCount++;
                        if (colonCount == 2) {
                            i += 3;

                            while(castList.charAt(i) != '"'){
                                character.append(castList.charAt(i));
                                i++;

                            }
                            i++;
                        }

                        else if(colonCount == 6) {
                            i += 3;
                            while(castList.charAt(i) != '"'){
                                name.append(castList.charAt(i));
                                i++;

                            }
                            i++;

                        }


                         if (colonCount == 7){
                             Actor actor = new Actor(name.toString(), values[1], character.toString());
                             actorList.add(actor);
                            colonCount = 0;
                            character.delete(0, character.length());
                            name.delete(0, name.length());
                        }

                    }

                }
            }
            stream.close();
        mergeSort actorSort = new mergeSort(actorList);
        ArrayList <Actor> people = actorSort.getSortedArray();

        System.out.println("Welcome to the movie wall");
        System.out.print("Enter the name of an actor (or " + '"'+ "EXIT" + '"' + "to quit):");
        Scanner scan = new Scanner(System.in);
        String actorName = scan.nextLine();
        while (actorName.compareTo("EXIT") != 0 ) {
            int minimum = 20;
            int index = 0;
            String wordSearch = "nothing";
            for (int i = 0; i < actorList.size(); i++) {

                if (people.get(i).getName().compareTo(actorName) == 0) {
                    index = i;
                    wordSearch = actorName;
                    break;


                }
                else if((Math.abs(people.get(i).getName().compareTo(actorName)) < minimum)) {
                    minimum = Math.abs(people.get(i).getName().compareTo(actorName));
                    wordSearch = people.get(i).getName();
                    index = i;
                }

            }

            if (wordSearch.equals(actorName)) {
                System.out.println("Actor: " + people.get(index).getName());
                for (int i = 0; i < actorList.size(); i++) {
                    if(people.get(i).getName().equals(actorName)) {
                        System.out.println("* Movie: " + people.get(i).getMovieChar());
                    }
                }
            }

            else if (!wordSearch.equals(actorName)){
                System.out.print("No such actor. Did you mean " + wordSearch + '"' + "(Y/N)" );
                String answer = scan.nextLine();
                if (Objects.equals(answer, "Y")) {
                    System.out.println("Actor: " + people.get(index).getName());
                    for (int i = 0; i < actorList.size(); i++) {
                        if (people.get(i).getName().equals(wordSearch)) {

                            System.out.println("* Movie:" + people.get(i).getMovieChar());
                        }
                    }
                }
            }
            System.out.print("Enter the name of an actor (or " + '"'+ "EXIT" + '"' + "to quit):");
            actorName = scan.nextLine();
        }

        System.out.println("Thank you for coming to the Movie Wall!");

    }
}

