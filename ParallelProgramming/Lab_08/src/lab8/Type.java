package lab8;

import javax.xml.bind.annotation.XmlElement;

public class Type {
    @XmlElement(name = "IsPeripheral", namespace = "urn:lab8")
    private boolean IsPeripheral;

    @XmlElement(name = "EnergyConsumption", namespace = "urn:lab8")
    private int EnergyConsumption;

    @XmlElement(name = "HasCooler", namespace = "urn:lab8")
    private boolean HasCooler;

    @XmlElement(name = "HasVoting", namespace = "urn:lab8")
    private String TypeGroup;

    @XmlElement(name = "Port", namespace = "urn:lab8")
    private String Port;

    public Type() {

    }

    public Type(boolean IsPeripheral, int EnergyConsumption, boolean HasCooler, String TypeGroup, String Port) {
       this.IsPeripheral = IsPeripheral;
       this.EnergyConsumption = EnergyConsumption;
       this.HasCooler = HasCooler;
       this.TypeGroup = TypeGroup;
       this.Port = Port;
    }

    public void setIsPeripheral(boolean IsPeripheral) {
        this.IsPeripheral = IsPeripheral;
    }

    public void setEnergyConsumption(int EnergyConsumption) {
        this.EnergyConsumption = EnergyConsumption;
    }

    public void setHasCooler(boolean HasCooler) {
        this.HasCooler = HasCooler;
    }

    public void setTypeGroup(String TypeGroup) {
        this.TypeGroup = TypeGroup;
    }

    public void setPort(String Port) {
        this.Port = Port;
    }

    public String toString() {
        return IsPeripheral + "," + EnergyConsumption + "," + HasCooler + "," + TypeGroup + "," + Port + ";";
    }
}
