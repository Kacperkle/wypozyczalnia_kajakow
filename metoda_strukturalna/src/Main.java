import java.util.*;

interface PaymentProcessor {
    void processPayment(double amount);
}

class PlaceholderPaymentProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Płatność się udała.");
    }
}

class Reservation {
    String kayakType;
    String dateTime;

    public Reservation(String kayakType, String dateTime) {
        this.kayakType = kayakType;
        this.dateTime = dateTime;
    }

    public String getKayakType() {
        return kayakType;
    }

    public String getDateTime() {
        return dateTime;
    }
}

class KayakRentalSystem {
    private PaymentProcessor paymentProcessor;
    private List<Reservation> reservations = new ArrayList<>();

    public KayakRentalSystem(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public void reserveKayak(String kayakType, String dateTime) {
        for (Reservation res : reservations) {
            if (res.getKayakType().equalsIgnoreCase(kayakType) && res.getDateTime().equals(dateTime)) {
                System.out.println("Błąd: Kajak " + kayakType + " jest już zarezerwowany na " + dateTime + ".");
                return;
            }
        }

        reservations.add(new Reservation(kayakType, dateTime));
        System.out.println("Kajak " + kayakType + " został zarezerwowany na " + dateTime + ".\n");
    }

    public void rentKayak(String kayakType, int rentalMinutes, double mony) {
        double price = rentalMinutes * mony;
        System.out.println("Wypożyczenie kajaka typu: " + kayakType + " na " + rentalMinutes + " minut.");
        System.out.println("Do zapłaty: " + price + " PLN");
        paymentProcessor.processPayment(price);
        System.out.println("Kajak wypożyczony pomyślnie!\n");
    }

    public void returnKayak(String kayakType) {
        System.out.println("Zwrot kajaka typu: " + kayakType);
        System.out.println("Kajak zwrócony pomyślnie!\n");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        KayakRentalSystem rentalSystem = new KayakRentalSystem(new PlaceholderPaymentProcessor());

        int choice = 0;
        while (choice != 4) {
            System.out.println("Witaj w wypożyczalni kajaków!");
            System.out.println("1 - Rezerwacja kajaka");
            System.out.println("2 - Wypożyczenie kajaka");
            System.out.println("3 - Zwrot kajaka");
            System.out.println("4 - Wyjdź");
            System.out.print("Twój wybór: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Podaj typ kajaka do rezerwacji (Jednoosobowy, Dwuosobowy, Sportowy, Rodzinny): ");
                    String kayakType = scanner.nextLine();
                    System.out.print("Podaj datę i godzinę rezerwacji (format: YYYY-MM-DD HH:MM): ");
                    String dateTime = scanner.nextLine();
                    rentalSystem.reserveKayak(kayakType, dateTime);
                }
                case 2 -> {
                    System.out.print("Podaj typ kajaka do wypożyczenia (1-Jednoosobowy, 2-Dwuosobowy): ");
                    int kayakType = scanner.nextInt();
                    System.out.print("Podaj przeznaczenie kajka (1-Sportowy, 2-Rodzinny): ");
                    int kayakPrzeznaczenie = scanner.nextInt();
                    double mony;
                    String kayakName;
                    if(kayakType == 1)
                    {
                        mony = 1.5;
                        kayakName = "Jednoosobowy";
                        if(kayakPrzeznaczenie == 1)
                        {
                            mony = mony+1;
                            kayakName = kayakName + " Sportowy";
                        }
                        else{
                            kayakName = kayakName + " Rodzinny";
                        }
                    }
                    else if(kayakType == 2){
                        mony = 2.0;
                        kayakName = "Dwuosobowy";
                        if(kayakPrzeznaczenie == 2)
                        {
                            mony = mony+1;
                            kayakName = kayakName + " Sportowy";
                        }
                        else{
                            kayakName = kayakName + " Rodzinny";
                        }
                    }
                    else{
                        System.out.println("Taki kajak nie istnieje.");
                        break;
                    }
                    System.out.print("Podaj czas wypożyczenia w minutach: ");
                    int rentalMinutes = scanner.nextInt();
                    scanner.nextLine();
                    rentalSystem.rentKayak(kayakName, rentalMinutes, mony);
                }
                case 3 -> {
                    System.out.print("Podaj typ kajaka do zwrotu (Jednoosobowy, Dwuosobowy, Sportowy, Rodzinny): ");
                    String kayakType = scanner.nextLine();
                    rentalSystem.returnKayak(kayakType);
                }
                case 4 -> System.out.println("Dziękujemy za korzystanie z naszej wypożyczalni!");
                default -> System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
            }
        }
        scanner.close();
    }
}
