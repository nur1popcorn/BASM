/*
 * Copyright (C) Keanu Poeschko - All Rights Reserved
 * Unauthorized copying of this file is strictly prohibited
 *
 * Created by Keanu Poeschko <nur1popcorn@gmail.com>, August 2017
 * This file is part of {BASM}.
 *
 * Do not copy or distribute files of {BASM} without permission of {Keanu Poeschko}
 *
 * Permission to use, copy, modify, and distribute my software for
 * educational, and research purposes, without a signed licensing agreement
 * and for free, is hereby granted, provided that the above copyright notice
 * and this paragraph appear in all copies, modifications, and distributions.
 *
 * {BASM} is based on this document: https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html
 *
 */

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
        + "Press me!"
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
