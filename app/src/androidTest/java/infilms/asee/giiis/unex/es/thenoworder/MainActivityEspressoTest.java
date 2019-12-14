package infilms.asee.giiis.unex.es.thenoworder;

import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivity =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void ensureFloatingButtonWork(){

        //Comprobar si el botón flotante cambia de pantalla

        //Dar click en el botón flotante
        onView(withId(R.id.new_order)).perform(click());

        //Comprobar que estamos en la pantalla de Selecionar la mesa
        onView(withId(R.id.tv_select_table)).check(matches(withText("Selecciona una mesa")));

        //Salir de la pantalla de seleccion de mesas
        pressBack();
    }


    @Test
    public void ensurePaymentScreenWork(){

        //Comprobar la pantalla de pago

        //Pulsar el botón de pagar del primer pedido
        onView(withIndex(withText("PAGAR"),0)).perform(click());

        //Deslizar hacia abajo la lista de productos del pedido
        onView(withId(R.id.rv_record_product_list)).perform(swipeDown());

        //Deslizar hacia arriba la lista de productos del pedido
        onView(withId(R.id.rv_record_product_list)).perform(swipeUp());

        //Introducir en el apartado de pago 0 euros y cerrar el teclado
        onView(withId(R.id.introduce_cash_et)).perform(typeText("0"),closeSoftKeyboard());

        //Comprobar que la cantidad introducida es insuficiente,
            // para ello que el textView tiene la frase "La cantidad no es suficiente"
        onView(withId(R.id.msg_bill_tv)).check(matches(withText("La cantidad no es suficiente")));

        //Comprobar que el botón de pagar esta deshabilitado, porque la cantidad no es suficiente
        onView(withText("Pagar")).check(matches(not(isEnabled())));

        //Reemplazar la cantidad del pago por 500
        onView(withId(R.id.introduce_cash_et)).perform(replaceText("500"),closeSoftKeyboard());

        //Comprobar que el boton de pagar esta habilitado porque la cantidad es suficiente
        onView(withId(R.id.pay_bill_button)).check(matches(isEnabled()));

        //Salir de la pantalla de pago
        pressBack();
    }


    @Test
    public void ensureAboutWork(){

        //Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        //onView(withText("Acerca de")).perform(click());

        //onView(withId(R.id.text_about)).check(matches(withText("The Now Order fue creada por:")));
    }


    @Test
    public void ensureHistoryWork(){
        //Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        //onData(withId(R.id.nav_view)).perform(swipeRight());

        //onView(withText("Historial")).perform(click());

        //onView(withText("CONSULTAR HISTORIAL")).perform(click());

    }


    @Test
    public void ensureSettingsWork(){

        //Comprobar que la pantalla de opciones funciona

        //Abrir el menu de opciones
        Espresso.openContextualActionModeOverflowMenu();

        //Pulsar en el item del menu para abrir la pantalla
        onView(withText("Settings")).perform(ViewActions.click());

        //Dar click a la opción para configurar el numero de mesas
        onView(withText("Numero de mesas")).perform(ViewActions.click());

        //Comprobar que tiene nueve mesas
        onView(withText("9")).check(matches(withText("9")));

        //Dar al boton de aceptar
        onView(withText("Aceptar")).perform(ViewActions.click());

        //Salir de la ajustes
        pressBack();
    }


    @Test
    public void ensureEditOrderWork(){

        //Comprobar que la pantalla de editar pedidos funcionan

        //Dar al boton de editar del primer pedido
        onView(withIndex(withText("EDITAR"),0)).perform(click());

        //Deslizar hacia abajo la lista de productos del pedido
        onView(withId(R.id.order_summary_products)).perform(swipeDown());

        //Deslizar hacia arriba la lista de productos del pedido
        onView(withId(R.id.order_summary_products)).perform(swipeUp());

        //Pulsar el botón flotante para añadir un nuevo producto
        onView(withId(R.id.add_new_product)).perform(click());

        //Pulsar en la pestaña comidas para ver las comidas
        onView(withText("COMIDAS")).perform(click());

        //Pulsar en la pestaña postres para ver los postres
        onView(withText("POSTRES")).perform(click());

        //Deslizar hacia abajo la lista de postres
        onView(withIndex(withId(R.id.rv_product_list_id),2)).perform(swipeDown());

        //Deslizar hacia arriba la lista de postres
        onView(withIndex(withId(R.id.rv_product_list_id),2)).perform(swipeUp());

        //Salir de la pantalla de seleccion de productos
        pressBack();

        //Salir de la pantalla de editar pedido
        pressBack();

    }



    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {

            int currentIndex = 0;

            @Override
            public void describeTo(org.hamcrest.Description description) {

                matcher.describeTo( description);

            }

            @Override
            public boolean matchesSafely(View view) {

                return matcher.matches(view) && currentIndex++ == index;

            }
        };
    }

















































































































}
