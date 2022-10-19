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
    public static int binSearch (ArrayList<Actor> actorList, String searchItem, int low, int high) {
        int mid;
        if (searchItem.compareTo(actorList.get(low).getName()) >= 0) {
            mid = low + (high - low) /2;

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
        actorSort.sortGivenArray();
        int val = 0;
        for(Actor i:actorSort.getSortedArray()){
            actorList.set(val, i);
            val++;
        }


        System.out.println("Welcome to the movie wall");
        System.out.print("Enter the name of an actor (or " + '"'+ "EXIT" + '"' + "to quit):");
        Scanner scan = new Scanner(System.in);
        String actorName = scan.nextLine();
        while (actorName.compareTo("EXIT") != 0 ) {

            int indexSearch = binSearch(actorList, actorName, 0, actorList.size() - 1);

            if (actorList.get(indexSearch).getName().compareTo(actorName) != 0) {
                System.out.print("No such actor. Did you mean " + actorList.get(indexSearch).getName() + '"' + "(Y/N)" );
                String answer = scan.nextLine();
                if (Objects.equals(answer, "Y")) {
                    String newName = actorList.get(indexSearch).getName();
                    System.out.println("Actor: " + actorList.get(indexSearch).getName());
                    while(actorList.get(indexSearch).getName().compareTo(newName) == 0) {
                        System.out.println("* Movie:" + actorList.get(indexSearch).getMovieChar());
                        indexSearch++;
                    }
                }
            }

            while(actorList.get(indexSearch).getName().compareTo(actorName) == 0) {
                System.out.println("* Movie:" + actorList.get(indexSearch).getMovieChar());
                indexSearch++;
            }

            System.out.print("Enter the name of an actor (or " + '"'+ "EXIT" + '"' + "to quit):");
            actorName = scan.nextLine();
        }

        System.out.println("Thank you for coming to the Movie Wall!");

    }
}

