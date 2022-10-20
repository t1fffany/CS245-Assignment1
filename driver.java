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


/**
 * Author: Tiffany Wilson
 * Version 1.0
 * Date: 10/12/22
 */

public class driver  {
    /**
     *
     * @param actorList the actor arrayList I want to search
     * @param searchItem the input search Item the user wants
     * @param low left-most value to be searched
     * @param high the right-most value to be searched
     * @return returns index of where the searchItem is found or what it resembles
     */
    public static int binSearch (ArrayList<Actor> actorList, String searchItem, int low, int high) {
        int mid;
        if (searchItem.compareTo(actorList.get(low).getName()) >= 0) {
            mid = low + (high - low) / 2;

            if (actorList.get(mid).getName().equals(searchItem)) {
                return mid;
            }

            if (actorList.get(mid).getName().compareTo(searchItem) > 0) {
                return binSearch(actorList, searchItem, low, mid -1 );
            }

            return binSearch(actorList, searchItem, mid + 1, high);
        }
        return low;
    }

    /**
     *
     * @param args the file we are reading through
     * @throws IOException if error while accessing the file
     */

    public static void main(String[] args) throws IOException{
        StringBuilder character = new StringBuilder();
        StringBuilder name = new StringBuilder();
        ArrayList<Actor> actorList = new ArrayList<>();

        InputStream stream = new FileInputStream(args[0]);
        try {
            CSVReader file = new CSVReaderBuilder(new InputStreamReader(stream, StandardCharsets.UTF_8)).withSkipLines(1).build();
            String[] values = null;
            int colonCount = 0;
            int region;
            int brace = 0;

            while ((values = file.readNext()) != null) {

                String castList = values[2];
                brace = castList.indexOf('}', brace);

                while(brace >= 0){

                    brace = castList.indexOf('}', brace+1);

                    int i = castList.indexOf(':');

                    colonCount += 1;

                    while (i >= 0 && colonCount < 7) {

                        i = castList.indexOf(':', i+1);
                        colonCount += 1;
                        if (colonCount == 2) {
                            region = i + 3;

                            while (castList.charAt(region) != '"') {

                                character.append(castList.charAt(region));
                                region++;

                            }
                        }
                        else if (colonCount == 6) {
                            region = i + 3;
                            while (castList.charAt(region) != '"') {
                                name.append(castList.charAt(region));
                                region++;

                            }
                        }

                    }
                }

                Actor actor = new Actor(name.toString(), values[1], character.toString());
                actorList.add(actor);
                colonCount = 0;
                character.delete(0, character.length());
                name.delete(0, name.length());
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        stream.close();
        mergeSort actorSort = new mergeSort(actorList);
        actorSort.sortGivenArray();
        int val = 0;
        actorList = (actorSort.getSortedArray());


        System.out.println("Welcome to the movie wall");
        System.out.print("Enter the name of an actor (or " + '"'+ "EXIT" + '"' + "to quit):");
        Scanner scan = new Scanner(System.in);

        //formats the user input
        String actorName = scan.nextLine().toLowerCase();
        StringBuilder nameChange = new StringBuilder(actorName);
        int space = nameChange.indexOf(" ");
        char actorFirst = (char)(((int) actorName.charAt(0)) - 32);
        char actorSec = (char)(((int) actorName.charAt(space + 1)) - 32);
        nameChange.setCharAt(0, actorFirst);
        nameChange.setCharAt(space + 1, actorSec);

        actorName = nameChange.toString();

        while (actorName.compareTo("EXIT") != 0 ) {

            int indexSearch = binSearch(actorList, actorName, 0, actorList.size() - 1);

            if (actorList.get(indexSearch).getName().compareTo(actorName) != 0) {
                System.out.print("No such actor. Did you mean " + '"'+ actorList.get(indexSearch).getName() + '"' + "(Y/N)" );
                String answer = scan.nextLine();
                if (Objects.equals(answer, "Y")) {
                    String newName = actorList.get(indexSearch).getName();
                    System.out.println("Actor: " + actorList.get(indexSearch).getName());

                    while(actorList.get(indexSearch).getName().compareTo(newName) == 0) {
                        System.out.println("* Movie: " + actorList.get(indexSearch).getMovieChar());
                        indexSearch++;
                    }
                }
                else if (! Objects.equals(answer, "N")) {
                    System.out.println("I will take that as a no...");
                }
            }

            while(actorList.get(indexSearch).getName().compareTo(actorName) == 0) {
                System.out.println("* Movie: " + actorList.get(indexSearch).getMovieChar());
                indexSearch++;
            }

            System.out.print("Enter the name of an actor (or " + '"'+ "EXIT" + '"' + "to quit):");
            actorName = scan.nextLine();
            if (! Objects.equals(actorName, "EXIT")) {
                //formats the user input
                actorName = actorName.toLowerCase();
                nameChange = new StringBuilder(actorName);
                space = nameChange.indexOf(" ");
                actorFirst = (char)(((int) actorName.charAt(0)) - 32);
                actorSec = (char)(((int) actorName.charAt(space + 1)) - 32);
                nameChange.setCharAt(0, actorFirst);
                nameChange.setCharAt(space + 1, actorSec);

                actorName = nameChange.toString();

            }
        }

        System.out.println("Thank you for coming to the Movie Wall!");

    }
}

