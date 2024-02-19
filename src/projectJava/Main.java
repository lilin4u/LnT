package projectJava;

import java.util.*;

class Karyawan {
    String kode;
    String nama;
    String jenisKelamin;
    String jabatan;
    double gaji;

    public Karyawan(String kode, String nama, String jenisKelamin, String jabatan, double gaji) {
        this.kode = kode;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.jabatan = jabatan;
        this.gaji = gaji;
    }

    @Override
    public String toString() {
        return "Kode: " + kode + ", Nama: " + nama + ", Jenis Kelamin: " + jenisKelamin + ", Jabatan: " + jabatan + ", Gaji: " + gaji;
    }
}

public class Main {
    static List<Karyawan> karyawanList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Insert Data Karyawan");
            System.out.println("2. View Data Karyawan");
            System.out.println("3. Update Data Karyawan");
            System.out.println("4. Delete Data Karyawan");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    insertDataKaryawan(scanner);
                    break;
                case 2:
                    viewDataKaryawan();
                    break;
                case 3:
                
                    break;
                case 4:

                    break;
                case 5:
                    System.out.println("Terima kasih!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    public static void insertDataKaryawan(Scanner scanner) {
        System.out.println("\nInput Data Karyawan:");
        System.out.print("Nama: ");
        String nama = scanner.nextLine();
    
        while (nama.length() < 3 || !nama.matches("[a-zA-Z]+")) {
            System.out.print("Input Nama karyawan [>= 3]: ");
            nama = scanner.nextLine();
        }
    
        System.out.print("Input Jenis Kelamin [Laki-laki | Perempuan] (Case Sensitive): ");
        String jenisKelamin = scanner.nextLine();
    
        while (!jenisKelamin.equals("Laki-laki") && !jenisKelamin.equals("Perempuan")) {
            System.out.println("Jenis kelamin harus Laki-laki atau Perempuan.");
            System.out.print("Input Jenis Kelamin [Laki-laki | Perempuan](Case Sensitive): ");
            jenisKelamin = scanner.nextLine();
        }
    
        System.out.print("Input Jabatan [Manager | Supervisor | Admin](Case Sensitive): ");
        String jabatan = scanner.nextLine();
    
        while (!jabatan.equals("Manager") && !jabatan.equals("Supervisor") && !jabatan.equals("Admin")) {
            System.out.println("Jabatan harus Manager, Supervisor, atau Admin.");
            System.out.print("Jabatan [Manager | Supervisor | Admin](Case Sensitive): ");
            jabatan = scanner.nextLine();
        }
    
        String kode = generateKodeKaryawan();
        double gaji = hitungGaji(jabatan);
    
        Karyawan karyawan = new Karyawan(kode, nama, jenisKelamin, jabatan, gaji);
        karyawanList.add(karyawan);
    
        System.out.println("Berhasil menambahkan karyawan dengan id " + kode);
    
        double bonusPercentage = 0;
        if (jabatan.equals("Manager")) {
            bonusPercentage = 0.10;
        } else if (jabatan.equals("Supervisor")) {
            bonusPercentage = 0.075;
        } else if (jabatan.equals("Admin")) {
            bonusPercentage = 0.05;
        }

        List<String> bonusRecipients = new ArrayList<>(); 
        for (Karyawan existingKaryawan : karyawanList) {
            if (existingKaryawan.jabatan.equals(jabatan)) {
                existingKaryawan.gaji += existingKaryawan.gaji * bonusPercentage;
                bonusRecipients.add(existingKaryawan.kode);
            }
        }

        if (!bonusRecipients.isEmpty()) {
            System.out.println("Bonus sebesar " + (bonusPercentage * 100) + "% telah diberikan kepada karyawan dengan id " + String.join(", ", bonusRecipients));
        }
    }
    
    

    public static void viewDataKaryawan() {
        if (karyawanList.isEmpty()) {
            System.out.println("Belum ada data karyawan yang tersedia.");
            return;
        }
    
        Collections.sort(karyawanList, Comparator.comparing(k -> k.nama));
    
        System.out.println("\nData Karyawan:");
        System.out.println("------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-12s | %-12s | %-12s |\n", 
                          "Kode", "Nama", "Jenis Kelamin", "Jabatan", "Gaji");
        System.out.println("------------------------------------------------------------------------------------");
        
        for (Karyawan karyawan : karyawanList) {
            System.out.printf("| %-10s | %-20s | %-12s | %-12s | Rp %-10.2f |\n", 
                               karyawan.kode, karyawan.nama, karyawan.jenisKelamin, karyawan.jabatan, karyawan.gaji);
        }
        System.out.println("------------------------------------------------------------------------------------");
    }
    

    public static String generateKodeKaryawan() {
        Random random = new Random();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder kode = new StringBuilder();

        // Generate random alphabet characters
        kode.append(alphabet.charAt(random.nextInt(alphabet.length())));
        kode.append(alphabet.charAt(random.nextInt(alphabet.length())));
        kode.append("-");

        kode.append(String.format("%04d", random.nextInt(10000)));

        for (Karyawan karyawan : karyawanList) {
            if (karyawan.kode.equals(kode.toString())) {
                return generateKodeKaryawan();
            }
        }

        return kode.toString();
    }
    

    public static double hitungGaji(String jabatan) {
        switch (jabatan) {
            case "Manager":
                return 8000000;
            case "Supervisor":
                return 6000000;
            case "Admin":
                return 4000000;
            default:
                return 0;
        }
    }
}
