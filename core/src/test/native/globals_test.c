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

#include "globals.h"

#include <assert.h>

int main() {
    {
        char *alphabet = strdup_or_die("a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z");
        int charc;
        char **charv = str_split(alphabet, ',', &charc);
        assert(charc == 26);
        for(int i = 0; i < 26; i++)
            assert(*charv[i] == "abcdefghijklmnopqrstuvwxyz"[i]);
        free(charv);
        free(alphabet);
    }

    {
        char *empty_text = strdup_or_die("");
        int c;
        str_split(empty_text, ',', &c);
        assert(c == 0);
        free(empty_text);
    }

    {
        char *empty_text = strdup_or_die("a");
        int c;
        char **v = str_split(empty_text, ',', &c);
        assert(c == 1);
        assert(*v[0] == empty_text[0]);
        free(v);
        free(empty_text);
    }
}
