# Copyright (C) Keanu Poeschko - All Rights Reserved
# Unauthorized copying of this file is strictly prohibited
#
# Created by Keanu Poeschko <nur1popcorn@gmail.com>, August 2017
# This file is part of {BASM}.
#
# Do not copy or distribute files of {BASM} without permission of {Keanu Poeschko}
#
# Permission to use, copy, modify, and distribute my software for
# educational, and research purposes, without a signed licensing agreement
# and for free, is hereby granted, provided that the above copyright notice
# and this paragraph appear in all copies, modifications, and distributions.
#
# {BASM} is based on this document: https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html

add_library(basm_agent
        SHARED
            ./native/src/agent.c
            ./native/src/cli.c
            ./native/src/globals.c
            ./native/src/socket.c

            ./native/include/cli.h
            ./native/include/globals.h
            ./native/include/socket.h
           )

target_include_directories(basm_agent
                        PUBLIC
                            ./native/include
                            ./native/src
                          )
