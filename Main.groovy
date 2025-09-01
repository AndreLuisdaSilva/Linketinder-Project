import controller.SistemaController
import view.Menu

class Main {
    static void main(String[] args) {
        //André Luis da Silva
        def menuView = new Menu()
        def controller = new SistemaController(menuView)
        controller.iniciar()
    }
}