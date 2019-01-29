
package Redbox;

public class Movie implements Comparable<Movie>
{
    String title;
    int avaliable, rented;
    
    Movie() 
    {
        avaliable = 0;
        rented = 0;
        title = "";
    }
    
    Movie(String t)
    {
        title = t;
        avaliable = 0;
        rented = 0;
    }
    
    Movie(String t, int a)
    {
        title = t;
        avaliable = a;
        rented = 0;
    }
    
    Movie(String t, int a, int r)
    {
        title = t;
        avaliable = a;
        rented = r;
    }
    
    public void setTitle(String t) { title = t;   }
    //return removes rent by one, increases avaliable by one
    public void returnCopy()       
    { 
        avaliable++; 
        rented--;
    }
    //rent removes avaliable by one, increases rented by one
    public void rentCopy()
    {
        avaliable--;
        rented++;
    }
    //removes num from avalible
    public void removeCopy(int num)
    {
        avaliable -= num;
    }
    //adds num to avaliable
    public void addCopy(int num)
    {
        avaliable += num;
    }
    //compares the titles of the movies
    public int compareTo(Movie compTitle)
    {
        return this.title.compareTo(compTitle.title);
    }
    
    public String getTitle() { return title; } 
    
    public int getRent() { return rented; }
    public int getAvaliable() { return avaliable; }
    
    @Override
    public String toString()
    {
        //left, 35 spaces, collumns 8 wide
        String tree = String.format("%-35s%-8d%-8d", title, avaliable, rented);
        tree += "\r\n"; //new line
        return tree;
    }
}
