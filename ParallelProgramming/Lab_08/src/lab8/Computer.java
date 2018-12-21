package lab8;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;


@XmlRootElement(name="Computer", namespace = "urn:lab8")
public class Computer {

    @XmlElement(name = "Name", namespace = "urn:lab8")
    private String Name;

    @XmlElement(name = "Origin", namespace = "urn:lab8")
    private String Origin;

    @XmlElement(name = "Type", namespace = "urn:lab8")
    private LinkedList<Type> Type;

    @XmlElement(name = "Price", namespace = "urn:lab8")
    private int Price;

    @XmlElement(name = "IsCritical", namespace = "urn:lab8")
    private boolean IsCritical;

    public Computer() {
        this.Type = new LinkedList<>();
    }

    public Computer(String Name, String Origin, LinkedList<Type> Type, int Price, boolean IsCritical) {
        this.Name = Name;
        this.Origin = Origin;
        this.Type = Type;
        this.Price = Price;
        this.IsCritical = IsCritical;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setOrigin(String Origin) {
        this.Origin = Origin;
    }

    public void addType(Type Type) {
        this.Type.add(Type);
    }

    public void setType(LinkedList<Type> Type) {
        this.Type = Type;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public void setIsCritical(boolean IsCritical) {
        this.IsCritical = IsCritical;
    }

    public String toString() {
        return Name + "\n" + Origin + "\n" + Type + "\n" + Price + "\n" + IsCritical;
    }
}
