#include "cli.h"

int main() {
    parse_options_or_die("-h,--help,-s,--halt");
    parse_options_or_die("");
}
