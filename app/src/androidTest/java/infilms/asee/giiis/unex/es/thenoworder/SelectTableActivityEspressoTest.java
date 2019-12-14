package infilms.asee.giiis.unex.es.thenoworder;


import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import infilms.asee.giiis.unex.es.thenoworder.ui.activitySelectTable.SelectTableActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SelectTableActivityEspressoTest {

    @Rule
    public ActivityTestRule<SelectTableActivity> SelectTableActivity =
            new ActivityTestRule<>(SelectTableActivity.class);


    @Test
    public void ensureCreateOrderWork(){

        //Comprobar si se puede crear un nuevo pedido

        //Comprobar que estamos en la pantalla de Selecionar la mesa
        onView(withId(R.id.tv_select_table)).check(matches(withText("Selecciona una mesa")));

        //Pulsar en la primera mesa
        onView(withIndex(withId(R.id.table_list_id),0)).perform(click());

        //Pulsar en el boton flotante
        onView(withId(R.id.add_new_product)).perform(click());

        //Pulsar en la pestaña comidas para ver las comidas
        onView(withText("COMIDAS")).perform(click());

        //Deslizar hacia abajo la lista de postres
        onView(withIndex(withId(R.id.rv_product_list_id),1)).perform(swipeDown());

        //Deslizar hacia arriba la lista de postres
        onView(withIndex(withId(R.id.rv_product_list_id),1)).perform(swipeUp());

        //Salir de la pantalla de seleccion de productos
        pressBack();

        //Salir de la pantalla de resumen de pedido
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
