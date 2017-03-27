package ru.apapikyan.learn.spring.core.hometask.ui.console.state;

import javafx.print.Collation;
import org.springframework.context.ApplicationContext;
import ru.apapikyan.learn.spring.core.hometask.domain.Auditorium;
import ru.apapikyan.learn.spring.core.hometask.service.AuditoriumService;

import java.util.Collection;
import java.util.Set;

/**
 * State for managing auditoriums
 *
 * @author Yuriy_Tkach
 */
public class AuditoriumManageState extends AbstractState {

    private AuditoriumService auditoriumService;

    public AuditoriumManageState(ApplicationContext context) {
        auditoriumService = context.getBean(AuditoriumService.class);
    }

    @Override
    protected int printMainActions() {
        System.out.println(" 1) Search auditorium by name");
        System.out.println(" 2) View all");
        return 2;
    }

    @Override
    protected void runAction(int action) {
        switch (action) {
            case 1:
                searchAuditorium();
                break;
            case 2:
                printDefaultInformation();
                break;
            default:
                System.err.println("Unknown action");
        }
    }

    private void searchAuditorium() {
        String searchTerm = readStringInput("Input auditorium name: ");
        Auditorium a = auditoriumService.getByName(searchTerm);
        if (a == null) {
            System.out.println("Not found (searched for: " + searchTerm + ")");
        } else {
            printAuditorium(a);
        }
    }

    @Override
    protected void printDefaultInformation() {
        System.out.println("All auditoriums:");
        Collection<Auditorium> all = auditoriumService.getAll();
        all.forEach(a -> printAuditorium(a));
    }

    private void printAuditorium(Auditorium a) {
        System.out.println(a.getName() + ", " + a.getNumberOfSeats() + " seats, vips: " + a.getVipSeats());
    }

}
