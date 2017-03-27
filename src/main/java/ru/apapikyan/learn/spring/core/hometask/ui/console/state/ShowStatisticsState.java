package ru.apapikyan.learn.spring.core.hometask.ui.console.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ru.apapikyan.learn.spring.core.hometask.domain.Auditorium;
import ru.apapikyan.learn.spring.core.hometask.service.AspectService;
import ru.apapikyan.learn.spring.core.hometask.service.AuditoriumService;

import java.util.Collection;

/**
 * State for managing auditoriums
 *
 * @author Yuriy_Tkach
 */
public class ShowStatisticsState extends AbstractState {

    @Autowired
    private AspectService aspectService;

    public ShowStatisticsState(ApplicationContext context) {
        aspectService = context.getBean(AspectService.class);
    }

    @Override
    protected int printMainActions() {
        //System.out.println(" 1) Search stat by ????");
        System.out.println(" 1) View all");
        return 1;
    }

    @Override
    protected void runAction(int action) {
        switch (action) {
//            case 1:
//                search...();
//                break;
            case 1:
                printDefaultInformation();
                break;
            default:
                System.err.println("Unknown action");
        }
    }

    @Override
    protected void printDefaultInformation() {
        System.out.println("All statistics:");

        for(Object stat : aspectService.getAll()) {
            System.out.println(stat.toString());
        }
    }

}
