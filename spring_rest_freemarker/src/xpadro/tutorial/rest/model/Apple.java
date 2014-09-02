package xpadro.tutorial.rest.model;


public class Apple implements Fruit{
	@FruitName("Apple")
    private String appleName;
    
    @FruitColor
    private String appleColor;
    
    @FruitProvider(id=1,name="陕西红富士集团",address="陕西省西安市延安路89号红富士大厦")
    private String appleProvider;
    
    public void setColor(String appleColor) {
        this.appleColor = appleColor;
    }
    public String getColor() {
        return appleColor;
    }
    
    public void setName(String appleName) {
        this.appleName = appleName;
    }
    public String getName() {
        return appleName;
    }
    
    public void setProvider(String appleProvider) {
        this.appleProvider = appleProvider;
    }
    public String getProvider() {
        return appleProvider;
    }
    
    public void displayName(){
        System.out.println("fruit's name is "+getName());
    }
    
    public void displayColor(){
        System.out.println("fruit's color is "+getColor());
    }
    
    public void displayProvider(){
    	System.out.println("fruit's providor is "+getProvider());
    }
}
