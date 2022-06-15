package com.Blood.types.Model;

public class Model {
    private String Name;
    private String Number;
    private String Location;
    private String Blood_type;
    // Empty Constructor
    public Model(){

    }
      //Constructor
    public Model(String name,String number,String Location,String blood_type){

        this.Name = name;
        this.Number = number;
        this.Location = Location;
        this.Blood_type = blood_type;

    }
    public void setName (String name )
    {
        this.Name = name;
    }
    public String getName()
    {
        return this.Name;
    }

    /**
     * this get and set
     * @return
     */
    public String getNumber() {
        return this.Number;
    }

    public void setNumber(String number) {
        this.Number = number;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getBlood_type() {
        return Blood_type;
    }

    public void setBlood_type(String blood_type) {
        Blood_type = blood_type;
    }
}
