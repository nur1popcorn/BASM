#include <stdio.h>

#include "globals.h"
#include "agent.c"

int main(int argc, char **argv) {
    parse_options_or_die("-s,--help");
    printf("Test\n");
}
