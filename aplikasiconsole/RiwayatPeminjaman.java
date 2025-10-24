package aplikasiconsole;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RiwayatPeminjaman {
    private static int nextId = 1;

    private final int idRiwayat;
    private final String nimMahasiswa;
    private final String namaMahasiswa;
    private final String idLaptop;
    private final String tipeLaptop;
    private final LocalDate tanggalPinjam;
    private String status;

    // Constructor
    public RiwayatPeminjaman(String nimMhs, String namaMhs, String idLaptop, String tipeLaptop) {
        this.idRiwayat = nextId++;
        this.nimMahasiswa = nimMhs;
        this.namaMahasiswa = namaMhs;
        this.idLaptop = idLaptop;
        this.tipeLaptop = tipeLaptop;
        this.tanggalPinjam = LocalDate.now();
        this.status = "Dipinjam";
    }

    // Method: Mencatat Peminjaman (Fitur 4)
    public static RiwayatPeminjaman catatPeminjaman(String nimMhs, String namaMhs, String idLaptop, String tipeLaptop) {
        return new RiwayatPeminjaman(nimMhs, namaMhs, idLaptop, tipeLaptop);
    }

    // Method: Mengubah Status (untuk simulasi pengembalian, opsional)
    public void ubahStatus(String statusBaru) {
        this.status = statusBaru;
    }

    // Method: Menyajikan Riwayat (Fitur 5)
    public String tampilkanDetail() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return String.format("| %-10s | %-12s | %-15s | %-9s | %-20s | %-12s | %-8s |",
            idRiwayat, nimMahasiswa, namaMahasiswa, idLaptop, tipeLaptop, 
            tanggalPinjam.format(formatter), status);
    }
}