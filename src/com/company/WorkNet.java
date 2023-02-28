package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WorkNet implements Serializable
{

    private static WorkNet _instance;

    public List<Client> clients = new ArrayList<>();

    public List<Cinema> cinemas = new ArrayList<>();

    private WorkNet(){}

    public static WorkNet getInstance()
    {
        if(_instance == null)
        {
            try
            {
                _instance = Load();
                System.out.println("Loaded");
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                _instance = new WorkNet();
                Main.InitHardcode();
            }
        }

        return _instance;
    }

    private static WorkNet Load() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("C:save.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        return  (WorkNet) objectInputStream.readObject();
    }
    public void Save() throws IOException {

        clients.removeIf(it -> it.getClass() == Administrator.class);

        var fStream = new FileOutputStream("C:save.txt");
        var outputStream = new ObjectOutputStream(fStream);
        outputStream.writeObject(this);
        outputStream.close();
    }
}
