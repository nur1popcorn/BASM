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

enable_testing()

add_executable(native_test
               native/test.c
              )

add_test(UnitTest native_test)

find_package(Java REQUIRED)
include(UseJava)

set(CMAKE_JAVA_COMPILE_FLAGS
    "-source" "1.8"
    "-target" "1.8")

add_jar(java_test
        native/testapp/TestApp.java
       )

add_test(NAME
         IntegrationTest
         COMMAND
         ${Java_JAVA_EXECUTABLE} -agentpath:../main/libbasm_agent.so
                                 -cp $<TARGET_PROPERTY:java_test,JAR_FILE> TestApp
        )
