package infilms.asee.giiis.unex.es.thenoworder;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class RecordDetailsActivityEspressoTest {

    @Rule
    public ActivityTestRule<RecordDetailsActivity> SelectTableActivity =
            new ActivityTestRule<>(RecordDetailsActivity.class);

    @Test
    public void ensurePayBillScreenWork(){

        //Comprobar la pantalla de historial

        //Comprobar que estamos de historial (Dado que esta el siguiente elemento)
        onView(withId(R.id.record_msg_total_price_tv)).check(matches(withText("Precio Total:")));

        //Comprobar que estamos de historial (Dado que esta el siguiente elemento)
        onView(withId(R.id.record_msg_table_tv)).check(matches(withText("Mesa")));

        //Comprobar que estamos de historial (Dado que esta el siguiente elemento)
        onView(withId(R.id.record_msg_id_tv)).check(matches(withText("id")));

        //Deslizar hacia abajo la lista de productos del pedido
        onView(withId(R.id.rv_record_product_list)).perform(swipeDown());

        //Deslizar hacia arriba la lista de productos del pedido
        onView(withId(R.id.rv_record_product_list)).perform(swipeUp());

        //Comprobar si el botón de borrar pedido esta activo
        onView(withId(R.id.record_table_tv)).check(matches(isEnabled()));

    }

}
