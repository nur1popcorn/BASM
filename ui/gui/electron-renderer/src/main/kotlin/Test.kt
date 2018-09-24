import button.Button
import button.ButtonVariant
import buttonbase.ButtonBase
import react.RBuilder
import react.dom.h1
import react.dom.render
import kotlin.browser.document

fun RBuilder.hello(name: String) {
    h1 {
        + "Hello, $name"
    }
    ButtonBase {
        + "Click me!"
    }
    Button(variant = ButtonVariant.CONTAINED) {
        + "Me too!"
    }
}

fun main(args : Array<String>) {
    render(document.getElementById("root")) {
        hello("Test")
    }
}
