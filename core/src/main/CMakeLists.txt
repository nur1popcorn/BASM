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
            ./native/src/assembler.c
            ./native/src/bit_vector.c
            ./native/src/byte_buffer.c
            ./native/src/cli.c
            ./native/src/globals.c
            ./native/src/graph.c
            ./native/src/linked_list.c
            ./native/src/socket.c
            ./native/src/x64_assembler.c

            ./native/include/allocation_algorithm.h
            ./native/include/assembler.h
            ./native/include/bit_vector.h
            ./native/include/byte_buffer.h
            ./native/include/cli.h
            ./native/include/function.h
            ./native/include/globals.h
            ./native/include/graph.h
            ./native/include/linked_list.h
            ./native/include/instruction.h
            ./native/include/socket.h
            ./native/include/x64_assembler.h
           )

target_include_directories(basm_agent
                        PUBLIC
                            ./native/include
                            ./native/src
                          )
