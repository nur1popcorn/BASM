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

package button

import child
import buttonbase.ButtonBaseProps
import react.RBuilder
import react.ReactElement

enum class ButtonColor {
    DEFAULT, INHERIT, PRIMARY, SECONDARY;

    private val COLORS = arrayOf(
        "inherit", "primary", "secondary")

    override fun toString(): String {
        return COLORS[ordinal]
    }
}

enum class ButtonSize {
    SMALL, MEDIUM, LARGE;

    private val SIZES = arrayOf(
        "small", "medium", "large")

    override fun toString() : String {
        return SIZES[ordinal]
    }
}

enum class ButtonVariant {
    TEXT, FLAT, OUTLINED, CONTAINED, RAISED, FAB, EXTENDED_FAB;

    private val VARIANTS = arrayOf(
        "text", "flat", "outlined", "contained", "raised", "fab", "extendedFab")

    override fun toString() : String {
        return VARIANTS[ordinal]
    }
}

interface ButtonProps : ButtonBaseProps {
    var color : String
    var disableFocusRipple : Boolean
    var fullWidth : Boolean
    var href : String
    var mini : Boolean
    var size : String
    var variant : String
}

/**
 * https://material-ui.com/api/button/
 */
fun RBuilder.Button(
    color : ButtonColor = ButtonColor.DEFAULT,
    component : dynamic = "button",
    disabled : Boolean = false,
    disableFocusRipple : Boolean = false,
    disableRipple : Boolean? = null,
    fullWidth : Boolean = false,
    href : String? = null,
    mini : Boolean = false,
    size : ButtonSize = ButtonSize.MEDIUM,
    variant : ButtonVariant = ButtonVariant.TEXT,
    block : RBuilder.() -> Unit
) : ReactElement {
    return child<ButtonProps>(default) {
        attrs {
            this.color = color.toString()
            this.component = component
            this.disabled = disabled
            this.disableFocusRipple = disableFocusRipple
            disableRipple?.let { this.disableFocusRipple = disableFocusRipple }
            this.fullWidth = fullWidth
            href?.let { this.href = href }
            this.mini = mini
            this.size = size.toString()
            this.variant = variant.toString()
        }
        block()
    }
}
