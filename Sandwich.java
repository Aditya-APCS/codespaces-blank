public class Sandwich {
    private String name;
    private int spiceLevel;
    // Constructor
    public Sandwich(String name, int spiceLevel) {
        this.name = name;
        this.spiceLevel = spiceLevel;

    }
    public String getName() {
        return name;
    }
    public void setName(String x){
        name=x;
    }
    
    public int getSpiceLevel(){
        return spiceLevel;
    }
    public void changeSpiceLevel(int p){
        spiceLevel=p;
    }

}


