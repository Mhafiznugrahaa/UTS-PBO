package aplikasiconsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AplikasiPeminjaman {

    // Aggregation: Mengelola List data Laptop, Mahasiswa, dan Riwayat
    private final List<Laptop> daftarLaptop;
    private final List<Mahasiswa> daftarMahasiswa;
    private final List<RiwayatPeminjaman> daftarRiwayat;

    // Dependency: Menggunakan Scanner sebagai parameter di beberapa method
    private final Scanner scanner;

    public AplikasiPeminjaman(Scanner scanner) {
        // this.scanner = new Scanner(System.in);
        this.scanner = scanner;
        this.daftarLaptop = new ArrayList<>();
        this.daftarMahasiswa = new ArrayList<>();
        this.daftarRiwayat = new ArrayList<>();
        menginisialisasiData();
    }

    // Method: Inisialisasi Data Awal
    private void menginisialisasiData() {
        // Data Dummy Laptop
        daftarLaptop.add(new Laptop("L1", "Lenovo ThinkPad T14", 5));
        daftarLaptop.add(new Laptop("L2", "Dell Latitude 5420", 0));
        daftarLaptop.add(new Laptop("L3", "HP ProBook G8", 3));

        // Data Dummy Mahasiswa (untuk validasi)
        daftarMahasiswa.add(new Mahasiswa("1201", "Budi Santoso"));
        daftarMahasiswa.add(new Mahasiswa("1202", "Siti Aisyah"));
    }

    // UTILS
    private void enterUntukLanjut() {
        System.out.println("-----------------------------------------");
        System.out.print("Tekan ENTER untuk melanjutkan...");
        scanner.nextLine();
    }

    // --- FITUR MAHASISWA (Peminjaman) ---

    // Method: Tampilkan Daftar Laptop (Fitur 1)
    public void tampilkanDaftarLaptop() {
        System.out.println("\n=========================================");
        System.out.println("DAFTAR LAPTOP TERSEDIA");
        System.out.println("=========================================");
        System.out.println("| ID | Tipe Laptop          | Stok | Status   |");
        System.out.println("|----|----------------------|------|----------|");
        for (Laptop l : daftarLaptop) {
            System.out.println(l.getInfo());
        }
        System.out.println("-----------------------------------------");
    }

    // Method: Proses Peminjaman (Fitur 2, 3, 4)
    public void prosesPinjam() {
        tampilkanDaftarLaptop();
        System.out.println("\n=========================================");
        System.out.println("FORMULIR PEMINJAMAN");
        System.out.println("=========================================");

        System.out.print("Masukkan NIM Anda: ");
        String nim = scanner.nextLine();
        
        // 1. Validasi Mahasiswa
        Mahasiswa mhs = null;
        for (Mahasiswa m : daftarMahasiswa) {
            if (m.getNim().equalsIgnoreCase(nim)) {
                mhs = m;
                break;
            }
        }

        if (mhs == null) {
            System.out.println("MAAF! NIM " + nim + " tidak terdaftar. Peminjaman dibatalkan.");
            return;
        }
        
        System.out.print("Masukkan ID Laptop yang Ingin Dipinjam: ");
        String idLaptop = scanner.nextLine();

        // 2. Validasi Laptop dan Stok (Laptop.cekStok() & Laptop.kurangiStok())
        Laptop laptopDipilih = null;
        for (Laptop l : daftarLaptop) {
            if (l.getId().equalsIgnoreCase(idLaptop)) {
                laptopDipilih = l;
                break;
            }
        }

        if (laptopDipilih == null) {
            System.out.println("MAAF! ID Laptop " + idLaptop + " tidak ditemukan.");
        } else if (laptopDipilih.cekStok() > 0) {
            
            // Lakukan Peminjaman dan Catat Riwayat
            boolean berhasil = laptopDipilih.kurangiStok();
            
            if (berhasil) {
                // RiwayatPeminjaman.catatPeminjaman()
                RiwayatPeminjaman riwayatBaru = RiwayatPeminjaman.catatPeminjaman(
                    mhs.getNim(), mhs.getNama(), laptopDipilih.getId(), laptopDipilih.getTipe()
                );
                daftarRiwayat.add(riwayatBaru);
                
                System.out.println("\n--- PEMBERITAHUAN ---");
                System.out.println("Laptop ID: " + laptopDipilih.getId() + " (" + laptopDipilih.getTipe() + ") berhasil dipinjam oleh " + mhs.getNama() + ".");
                System.out.println("Sistem mencatat riwayat peminjaman. Stok tersisa: " + laptopDipilih.cekStok());
            }

        } else {
            System.out.println("\nMAAF! Stok untuk Laptop ID: " + laptopDipilih.getId() + " (" + laptopDipilih.getTipe() + ") HABIS (Stok 0).");
            System.out.println("Peminjaman DITOLAK.");
        }
    }

    // --- FITUR ADMIN (Monitoring) ---

    // Method: Tampilkan Riwayat Admin (Fitur 5)
    public void tampilkanRiwayatAdmin() {
        System.out.println("\n=========================================");
        System.out.println("RIWAYAT SELURUH PEMINJAMAN");
        System.out.println("=========================================");
        
        if (daftarRiwayat.isEmpty()) {
            System.out.println("Belum ada riwayat peminjaman yang tercatat.");
            return;
        }

        System.out.println("| ID Riwayat | NIM Peminjam | Nama Peminjam   | ID Laptop | Tipe Laptop          | Tgl Pinjam   | Status   |");
        System.out.println("|------------|--------------|-----------------|-----------|----------------------|--------------|----------|");

        // RiwayatPeminjaman.tampilkanSemua()
        for (RiwayatPeminjaman r : daftarRiwayat) {
            System.out.println(r.tampilkanDetail());
        }
    }

    // --- MENU INTERAKSI ---

    private void menuMahasiswa() {
        int pilihan = 0;

        do {
            System.out.println("\n=========================================");
            System.out.println("MENU MAHASISWA");
            System.out.println("=========================================");
            System.out.println("1. Lihat Daftar Laptop Tersedia"); // Fitur 1
            System.out.println("2. Pinjam Laptop"); // Fitur 2, 3, 4
            System.out.println("3. Kembali ke Menu Utama");
            System.out.println("-----------------------------------------");
            System.out.print("Masukkan Pilihan Anda (1-3): ");
            

            try {
                pilihan = Integer.parseInt(scanner.nextLine());
                switch (pilihan) {
                    case 1
                        -> tampilkanDaftarLaptop();
                        // break;
                    case 2
                        -> prosesPinjam();
                        // break;
                    case 3
                        -> System.out.println("Kembali ke Menu Utama...");
                        // break; // Tidak perlu lagi 'break'
                    default
                        -> System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka.");
                pilihan = 0; // Reset pilihan agar loop berlanjut
            }
            if (pilihan != 3) enterUntukLanjut();
        } while (pilihan != 3);
    }
    
    private void menuAdmin() {
        int pilihan = 0;
        do {
            System.out.println("\n=========================================");
            System.out.println("MENU ADMIN");
            System.out.println("=========================================");
            System.out.println("1. Lihat Semua Riwayat Peminjaman"); // Fitur 5
            System.out.println("2. Kembali ke Menu Utama");
            System.out.println("-----------------------------------------");
            System.out.print("Masukkan Pilihan Anda (1-2): ");
            
            try {
                pilihan = Integer.parseInt(scanner.nextLine());
                switch (pilihan) {
                    case 1
                        -> tampilkanRiwayatAdmin();
                        // break;
                    case 2
                        -> System.out.println("Kembali ke Menu Utama...");
                        // break;
                    default
                        ->System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka.");
                pilihan = 0;
            }
            if (pilihan != 2) enterUntukLanjut();
        } while (pilihan != 2);
    }


    // Method: Mengelola Alur Program (Main Program Loop)
    public void jalankan() {
        int pilihan = 0;
        boolean isRunning = true;
        
        while (isRunning) {
            System.out.println("\n=========================================");
            System.out.println("APLIKASI PINJAM LAPTOP KAMPUS");
            System.out.println("=========================================");
            System.out.println("Pilih Tipe Pengguna:");
            System.out.println("1. Mahasiswa (Peminjaman)");
            System.out.println("2. Admin (Monitoring)");
            System.out.println("3. Keluar");
            System.out.println("-----------------------------------------");
            System.out.print("Masukkan Pilihan Anda (1-3): ");

            try {
                pilihan = Integer.parseInt(scanner.nextLine());
                switch (pilihan) {
                    case 1
                        -> menuMahasiswa();
                        // break;
                    case 2
                        -> menuAdmin();
                        // break;
                    case 3 
                        -> { isRunning = false;
                        System.out.println("\nTerima kasih telah menggunakan Aplikasi Peminjaman Laptop.");
                        // break;
                    }
                    default
                        -> System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka. Silakan coba lagi.");
            }
        }
        scanner.close();
    }

    // MAIN METHOD
    public static void main(String[] args) {
        // Inisialisasi Scanner hanya di sini, menggunakan try-with-resources
        try (Scanner scanner = new Scanner(System.in)) { 
            AplikasiPeminjaman app = new AplikasiPeminjaman(scanner); // Kirim scanner ke constructor
            app.jalankan();
        } // Scanner akan otomatis tertutup di akhir blok try
        // AplikasiPeminjaman app = new AplikasiPeminjaman();
        // app.jalankan();
    }
}