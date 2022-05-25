package vaccopsjava;

import java.util.*;
import java.util.stream.Collectors;

public class VaccOps implements IVaccOps {

    private List<Doctor> doctorsList;
    private List<Patient> patientsList;
    private Map<Doctor, List<Patient>> docP;

    public VaccOps() {
        this.doctorsList = new ArrayList<>();
        this.patientsList = new ArrayList<>();

        this.docP = new HashMap<>();
    }

    public void addDoctor(Doctor d) {

        for (Doctor doctor : this.doctorsList) {
            if (doctor.name.equals(d.name)) {
                throw new IllegalArgumentException();
            }
        }
        this.doctorsList.add(d);
        this.docP.put(d, new ArrayList<>());
    }

    public void addPatient(Doctor d, Patient p) {
        Doctor doctor1 = seachD(d.name);

        if (doctor1 == null) {
            throw new IllegalArgumentException();
        }

        this.patientsList.add(p);
        if (docP.get(d).contains(p)) {
            throw new IllegalArgumentException();
        }
        List<Patient> patients = docP.get(d);
        patients.add(p);
        docP.putIfAbsent(d, patients);


    }

    private Doctor seachD(String name) {
        for (Doctor doctor : doctorsList) {
            if (doctor.name.equals(name)) {
                return doctor;
            }
        }
        return null;
    }

    private Patient searchP(String name) {
        for (Patient patient : patientsList) {
            if (patient.name.equals(name)) {
                return patient;
            }
        }
        return null;
    }


    public Collection<Doctor> getDoctors() {
        return this.doctorsList;
    }

    public Collection<Patient> getPatients() {
        System.out.println(this.patientsList);
        return this.patientsList;
    }

    public boolean exist(Doctor d) {
        return seachD(d.name) != null;
    }

    public boolean exist(Patient p) {
        return searchP(p.name) != null;
    }


    public Doctor removeDoctor(String name) {
        if (seachD(name) == null) {
            throw new IllegalArgumentException();
        }
        Doctor doctor = seachD(name);
        this.doctorsList.remove(doctor);
        List<Patient> patients = this.docP.get(doctor);
        patients = new ArrayList<>();
        this.docP.remove(doctor);

        return doctor;
    }

    public void changeDoctor(Doctor from, Doctor to, Patient p) {
        Doctor doctorfrom = seachD(from.name);
        Doctor doctorto = seachD(to.name);
        Patient patient = searchP(p.name);
        if (doctorfrom == null || doctorto == null || patient == null) {
            throw new IllegalArgumentException();
        }
        List<Patient> patients = this.docP.get(doctorfrom);
        this.docP.put(doctorto, patients);
        patients.remove(p);
        this.docP.put(doctorfrom, patients);


    }

    public Collection<Doctor> getDoctorsByPopularity(int populariry) {
        return this.doctorsList
                .stream()
                .sorted(Comparator.comparingInt(a -> a.popularity))
                .collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsByTown(String town) {
        return this.patientsList
                .stream()
                .sorted(Comparator.comparing(a -> a.town))
                .collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsInAgeRange(int lo, int hi) {
        return this.patientsList
                .stream()
                .filter(p -> p.age >= lo && p.age <= hi)
                .collect(Collectors.toList());

    }

    public Collection<Doctor> getDoctorsSortedByPatientsCountDescAndNameAsc() {

        HashSet<Doctor> dc = new HashSet<>();

        docP.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().size() - a.getValue().size());
        docP.keySet()
                .stream()
                .sorted(Comparator.comparing(a -> a.name));
        dc.addAll(docP.keySet());
        return dc;

//  map.entrySet().stream()
//                .filter(i -> i.getValue().size() > 0).sorted((f, s) ->
//                s.getValue().size() - f.getValue().size())
//                .forEach(element -> {
//                    if (element.getValue().size() != 0) {
//                        System.out.printf("Side: %s, Members: %d%n", element.getKey(), element.getValue().size());
//
//                        element.getValue().stream().sorted(Comparator.naturalOrder())
//                                .forEach(el -> System.out.printf("! %s%n", el));
//
//
//                    }
//                });
//
//
//    }

    }

    public Collection<Patient> getPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAge() {
        List<Patient> res = new ArrayList<>();


        return res;
    }
}
