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

set(unit_tests
    "test"
    "byte_buffer_test"
    "cli_test"
    "globals_test"
    "graph_test"
    "linked_list_test"
    "socket_test")

set(THREADS_PREFER_PTHREAD_FLAG ON)
find_package(Threads REQUIRED)

foreach(test ${unit_tests})
    add_executable(unit_${test} native/${test}.c)
    target_include_directories(unit_${test} PUBLIC basm_agent)
    target_link_libraries(unit_${test} basm_agent Threads::Threads)
    add_test(unit_test unit_${test})
endforeach()

find_package(Java REQUIRED)
include(UseJava)

set(CMAKE_JAVA_COMPILE_FLAGS
    "-source" "1.8"
    "-target" "1.8")

set(integration_tests
    "TestApp")

foreach(itest ${integration_tests})
    add_jar(integration_${itest} native/testapps/${itest}.java)
    add_test(NAME integration_${itest} COMMAND
                ${Java_JAVA_EXECUTABLE} -agentpath:../main/libbasm_agent.so
                -cp $<TARGET_PROPERTY:integration_${itest},JAR_FILE>
                integration_${itest}
            )
endforeach()
