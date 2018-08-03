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

cmake_minimum_required(VERSION 3.5)
project(basm_agent C)

set(CMAKE_C_STANDARD 11)

find_package(JNI REQUIRED)
include_directories(${JAVA_INCLUDE_PATH})

if(UNIX)
    include_directories(${JAVA_INCLUDE_PATH}/linux/)
endif()

add_subdirectory(main)
add_subdirectory(test)
