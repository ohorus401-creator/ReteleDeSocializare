package ui;

import domain.Prietenie;
import domain.Utilizator;
import exceptions.DuplicateEntityException;
import exceptions.InexistentIdException;
import exceptions.RepositoryException;
import exceptions.ValidationException;
import service.PrietenieService;
import service.ReteaService;
import service.UserService;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ConsoleUI {
    private final UserService userService;
    private final PrietenieService prietenieService;
    private final ReteaService reteaService;
    private Scanner scanner;

    public ConsoleUI(UserService userService, PrietenieService prietenieService, ReteaService reteaService) {
        this.userService = userService;
        this.prietenieService = prietenieService;
        this.reteaService = reteaService;
        this.scanner = new Scanner(System.in);
    }

    private void showMenu(){
        System.out.println("1.Afiseaza utilizatori.");
        System.out.println("2.Adauga utilizator.");
        System.out.println("3.Sterge utilizator.");
        System.out.println("4.Afiseaza prieteni.");
        System.out.println("5.Adauga prieten.");
        System.out.println("6.Sterge prieten.");
        System.out.println("7.Afiseaza numarul de comunitati.");
        System.out.println("8.Afiseaza cea mai sociabila comunitate.");
        System.out.println("0.Exit");
    }

    private void afiseazaUtilizatori(){
        List<Utilizator> useri = userService.getAllUsers();
        if (useri.isEmpty()){
            System.out.println("Nu exista utilizatori inregistrati!");
        } else{
            System.out.println("Lista utilizatorilor inregistrati!");
            for ( Utilizator user : useri){
                System.out.println("(" + user.getId() + ") " + user.getPrenume() + " " + user.getNume());
            }
        }
    }

    private void adaugaUtilizator(){
        try{
            System.out.println("Introdu id ul utilizatorului: ");
            String input = scanner.nextLine();
            Long id = Long.parseLong(input);

            System.out.println("Introdu prenumele utilizatorului");
            String prenume = scanner.nextLine();

            System.out.println("Introdu numele utilizatorului");
            String nume = scanner.nextLine();

            userService.addUser(id, prenume, nume);
            System.out.println("Utilizator adaugat!");

        } catch (NumberFormatException e){
            System.out.println("Id invalid: trebuie sa fie un numar");
        } catch (DuplicateEntityException | ValidationException | RepositoryException e){
            System.out.println(e.getMessage());
        }
    }

    private void stergeUtilizator(){
        try{
            System.out.println("Introdu id ul utilizatorului: ");
            String input = scanner.nextLine();
            Long id = Long.parseLong(input);

            userService.removeUser(id);
            System.out.println("Utilizator sters cu succes!");
        } catch (NumberFormatException e){
            System.out.println("Id invalid: trebuie sa fie un numar");
        } catch (InexistentIdException | RepositoryException e){
            System.out.println(e.getMessage());
        }
    }

    private void afiseazaPrietenii(){
        List<Prietenie> prietenii = prietenieService.getAllFriendships();
        if (prietenii.isEmpty()){
            System.out.println("Nu exista nici o relatie de prietenie intre utilizatori!");
        } else{
            System.out.println("Lista prieteniilor existente!");
            for ( Prietenie friend : prietenii){
                System.out.println(" User 1: " + friend.getIdUser1() + " - User 2: " + friend.getIdUser2() + " (" + friend.getData() + ")");
            }
        }
    }

    private void adaugaPrietenie(){
        try{
            System.out.println("Introdu id ul primului utilizator: ");
            String input = scanner.nextLine();
            Long id1 = Long.parseLong(input);

            System.out.println("Introdu id ul celui de al doilea utilizator: ");
            input = scanner.nextLine();
            Long id2 = Long.parseLong(input);

            prietenieService.addFriendship(id1,id2);
            System.out.println("Prietenie adaugata cu succes!");

        }catch (NumberFormatException e){
            System.out.println("Id invalid: trebuie sa fie un numar!");
        } catch (DuplicateEntityException | InexistentIdException | ValidationException | RepositoryException e ){
            System.out.println(e.getMessage());
        }
    }

    private void stergePrietenie() {
        try {
            System.out.println("Introdu id ul primului utilizator: ");
            String input = scanner.nextLine();
            Long id1 = Long.parseLong(input);

            System.out.println("Introdu id ul celui de al doilea utilizator: ");
            input = scanner.nextLine();
            Long id2 = Long.parseLong(input);

            prietenieService.removeFriendship(id1, id2);
            System.out.println("Prietenie stearsa cu succes!");

        } catch (NumberFormatException e) {
            System.out.println("Id invalid: trebuie sa fie un numar!");
        } catch (InexistentIdException | ValidationException | RepositoryException e) {
            System.out.println(e.getMessage());
        }
    }

    private void numarComunitati(){
        int nr = reteaService.numarComunitati();
        System.out.println("Numarul de comunitati din retea este " + nr + "!");
    }

    private void ceaMaiSociabilaComunitate(){
        Set<Long> comunitate = reteaService.ceaMaiMareComunitate();
        if (comunitate == null || comunitate.isEmpty()){
            System.out.println("Nu exista comunitati!");
        } else{
            System.out.println("Cea mai mare comunitate are " + comunitate.size()
                    + " utilizatori, cu id-urile: " + comunitate);
        }
    }

    public void runUi(){
        boolean running = true;
        while(running){
            showMenu();
            System.out.println("Introdu optiunea: ");
            String comanda = scanner.nextLine().trim();
            switch (comanda) {
                case "0":  running = false; break;
                case "1": afiseazaUtilizatori(); break;
                case "2": adaugaUtilizator(); break;
                case "3": stergeUtilizator(); break;
                case "4": afiseazaPrietenii(); break;
                case "5": adaugaPrietenie(); break;
                case "6": stergePrietenie(); break;
                case "7": numarComunitati(); break;
                case "8": ceaMaiSociabilaComunitate(); break;
                default: System.out.println("Comanda invalida!");
            }
        }
    }
}
