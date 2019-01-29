package Redbox;
import java.util.*;
import java.io.*;

/* 
Movies will be stored in a binary tree. The transaction files will contain
the actions. Movies can either be deleted, added, rented, or returned
errors are sent to the errors log. 
inventory contains the movies. movies.txt is the output file. 
The format is this: Name of movie, number of copies avaliable, 
and number of copies rented
*/
public class Main 
{

    public static void main(String[] args) throws IOException
    {
       BinaryTree<Movie> movieRental;
       movieRental = new BinaryTree<>();
       String line = "";
       File file = new File("inventory.dat");
       File orderFile = new File("transaction.log");
       PrintWriter output = new PrintWriter(new File("movies.txt"));
       
       //checks to see if the file opens successfully 
       if(file.exists() == false || orderFile.exists() == false)
       {
           System.out.println("The file could not be found");
           
           return;
       }
       //Get the input from the file
       Scanner input = new Scanner(file);
       getInput(input, movieRental);
       input.close();
       
       input = new Scanner(orderFile);
       
       processOrders(input, movieRental);
       output.println(movieRental.toString());
       
       output.close();

    }
    
    public static void processOrders(Scanner input, BinaryTree orders) throws FileNotFoundException
    {
        //action is the add, remove, rent, or return
        String line = "", hold = "", action = "", title = "";
        PrintWriter errors = new PrintWriter(new File("error.log"));
        int index = 0, numAdd = 0;
        //while the file isn't empty
        while(input.hasNext())
        {
            try //if an exception is thrown, print the string to the file
            {
                //get the first line of the file
                line = input.nextLine();
                hold = String.valueOf(line);
                index = line.indexOf(' '); //get the first space
                //the action is stored between the first space and the beginning
                //of the line, so we can use this to substring it otu.
                action = line.substring(0, index);
                line = line.substring(index + 2);

               
                index = line.indexOf('\"'); //get the index of the next quote
                title = line.substring(0, index); //the title is between the quote
                line = line.substring(index); 

                //search for the movie. if it returns a null, then the movie 
                //isn't in the list
                Movie update = new Movie();
                update = (Movie)orders.search(new Movie(title)); 
                //if the action is add
                if(action.compareTo("add") == 0)
                {
                    //get the number at the end of the file
                    line = line.substring(2);
                    numAdd = Integer.parseInt(line);
                    //if the movie doesnt exist in the file, insert it
                    if(update == null)
                        orders.insert(new Movie(title, numAdd, 0));
                    else //otherwise, add more copies to the redbox
                        update.addCopy(numAdd);
                }
                //if the action is rent
                else if(action.compareTo("rent") == 0)
                {
                    //increase avaliable by 1, decrease rented by 1
                    line = line.substring(1);
                    if(!line.equals(""))
                    {
                        errors.println(hold);
                        continue;
                    }
                    update.rentCopy();
                }
                //if the action is return
                else if(action.compareTo("return") == 0)
                {
                    line = line.substring(1);
                    //if theres anything left in the string, the input is invalid
                    if(!line.equals(""))
                    {
                        errors.println(hold);
                        continue;
                    }
                    //return the copy to the tree
                    update.returnCopy();
                }
                //if the action is remove
                else if(action.compareTo("remove") == 0)
                {
                    //convert the string to a number
                    line = line.substring(2);
                    numAdd = Integer.parseInt(line);
                    //call update
                    
                    update.removeCopy(numAdd);
                    //if we have 0 rented and 0 avaliable, call the delete
                    if(update.getRent() == 0 && update.getAvaliable() == 0)
                        orders.delete(update);
                }
                else //if the action is none of these, it must be an error
                {
                    errors.println(hold);
                    continue;
                }
            }
            catch(Exception Error)
            {
                //if an exception is throw, print to the errors file
                errors.println(hold);
                continue;
            }
                
        }
        errors.close();
        
    }
    
    public static void getInput(Scanner input, BinaryTree inputTree) throws IOException
    {
        String line = "", hold = "", title = "";
   
        int index = 0, numA = 0, numR = 0;
        //index keeps track of the string
        //numA is the number of copies of a movie avaliable
        //numR is the number of time the movie has been rented
        
        //while the file isn't empty
        while(input.hasNext())
        {
            line = input.nextLine();
            
            /*the movie title is contained within quotes so we need to get the
            name from the inside of the quotes. First, cut the quote out of
            the line. it will be in the first element. Then, we will grab the
            index of the next quote. all of the elements in the string before
            this index will be the title of the movie*/
            
            
            line = line.substring(1); //cut out the first quote
            index = line.indexOf('\"'); //get the index of the next quote
            title = line.substring(0, index); //title = elements from 0 to index
            line = line.substring(line.indexOf('\"')); //cuts off everything before ","
            
            /*Now we need to get the quantity. This is an integer surrounded
            by 2 commas. we will hold onto the integer with the the hold string
            then conver the hold strint into an integer
            */
            //first we will cut off the first comma
            line = line.substring(2);
            hold = line.substring(0, line.indexOf(','));
            
            numA = Integer.parseInt(hold); //converts the string to an integer
            
            
            /*cut off the second comma, leaving us with just the number 
            of rented copies */
            
            line = line.substring(line.indexOf(','));
            line = line.substring(1);
            numR = Integer.parseInt(line); //convert the string to an int
            Movie insertMovie = new Movie(title, numA, numR);
            
            
            inputTree.insert(insertMovie);
            
        }
    }
    
}
