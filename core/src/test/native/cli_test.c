#include "cli.h"

int main() {
    parse_options_or_die("-h,--help,-b=16464,--bind=123");
    parse_options_or_die("");
}
