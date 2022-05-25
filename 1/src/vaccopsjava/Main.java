package vaccopsjava;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
VaccOps vaccOps =   new VaccOps();

        Doctor d1 = new Doctor("a", 1);
        Doctor d2 = new Doctor("b", 1);
        Doctor d3 = new Doctor("c", 1);
        Patient p1 = new Patient("a", 1, 1, "a");
        Patient p2 = new Patient("b", 1, 2, "b");
        Patient p3 = new Patient("c", 1, 3, "c");

        Doctor d4 = new Doctor("d", 3);
        Doctor d5 = new Doctor("e", 4);
        Doctor d6 = new Doctor("f", 4);
        Doctor d7 = new Doctor("g", 2);
        Doctor d8 = new Doctor("h", 2);
        Patient p4 = new Patient("d", 3, 1, "a");
        Patient p5 = new Patient("e", 3, 1, "a");
        Patient p6 = new Patient("f", 2, 1, "a");
        Patient p7 = new Patient("g", 5, 10, "a");
        Patient p8 = new Patient("h", 5, 5, "a");
        Patient p9 = new Patient("i", 5, 50, "a");
        Patient p10 = new Patient("j", 2, 2, "a");
        Patient p11 = new Patient("k", 1, 2, "a");


        vaccOps.addDoctor(d1);
        ArrayList<Doctor> d = new ArrayList<>(vaccOps.getDoctors());
        System.out.println(d.size());
      //  System.out.println(d1.get(0).name);
        System.out.println(d.get(0).popularity);
        System.out.println(d1.popularity);
    }
}
