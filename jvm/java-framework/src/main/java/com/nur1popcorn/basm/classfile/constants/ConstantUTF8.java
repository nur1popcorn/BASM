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

package com.nur1popcorn.basm.classfile.constants;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.nur1popcorn.basm.Constants.CONSTANT_UTF8;

/**
 * The {@link ConstantUTF8} represents a simple string value.
 * <a href="https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.7">
 *     CONSTANT_Utf8 4.4.7
 * </a>
 *
 * <p>Strings are encoded as follows: </p>
 * <ul>
 *     <li>
 *         <p>Characters in range from 1 to 7f are not encoded using 1 byte.</p>
 *         <p>Unlike 2 and 3 byte characters 1 byte characters will hence never set the last bit to 1.</p>
 *         <table summary="A binary representation of a valid 1 byte character.
 *                         ?s are either 1 or 0 every thing else is fixed.
 *                         Since they range from 1 to 7f there is no way for all the bits to be 0 at once."
 *                border="1">
 *             <tbody>
 *                 <tr>
 *                     <td>0</td> <td>?</td> <td>?</td> <td>?</td> <td>?</td> <td>?</td> <td>?</td> <td>?</td>
 *                 </tr>
 *             </tbody>
 *         </table>
 *     </li>
 *     <li>
 *         <p>Characters in range from 80 to 7ff are encoded using 2 bytes.</p>
 *         <table summary="A binary representation of a valid 2 byte character."
 *                border="1">
 *             <tbody>
 *                 <tr>
 *                     <td>1</td> <td>1</td> <td>0</td> <td>?</td> <td>?</td> <td>?</td> <td>?</td> <td>?</td>
 *                 </tr>
 *                 <tr>
 *                     <td>1</td> <td>0</td> <td>?</td> <td>?</td> <td>?</td> <td>?</td> <td>?</td> <td>?</td>
 *                 </tr>
 *             </tbody>
 *         </table>
 *     </li>
 *     <li>
 *         <p>Characters in range from 800 to ffff are encoded using 3 bytes.</p>
 *         <table summary="A binary representation of a valid 3 byte character."
 *                border="1">
 *             <tbody>
 *                 <tr>
 *                     <td>1</td> <td>1</td> <td>1</td> <td>0</td> <td>?</td> <td>?</td> <td>?</td> <td>?</td>
 *                 </tr>
 *                 <tr>
 *                     <td>1</td> <td>0</td> <td>?</td> <td>?</td> <td>?</td> <td>?</td> <td>?</td> <td>?</td>
 *                 </tr>
 *                 <tr>
 *                     <td>1</td> <td>0</td> <td>?</td> <td>?</td> <td>?</td> <td>?</td> <td>?</td> <td>?</td>
 *                 </tr>
 *             </tbody>
 *         </table>
 *     </li>
 * </ul>
 *
 * @see ConstantInfo
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class ConstantUTF8 extends ConstantInfo {
    /*
     *
     */
    public String bytes;

    /**
     * @param bytes the string from which the string {@link ConstantInfo} is supposed to
     *              be constructed.
     */
    public ConstantUTF8(String bytes) {
        super(CONSTANT_UTF8);
        this.bytes = bytes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(DataOutputStream os) throws IOException {
        super.write(os);
        os.writeUTF(bytes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(ConstantVisitor visitor) {
        visitor.visitUTF8(this);
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof ConstantUTF8) {
            final ConstantUTF8 constantUtf8 = (ConstantUTF8) other;
            return (constantUtf8.bytes == null && bytes == null) ||
                    bytes.equals(constantUtf8.bytes);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return bytes.hashCode() * 33 ^ getTag();
    }

    @Override
    public String toString() {
        return super.toString() + "[" +
            bytes +
        "]";
    }
}
