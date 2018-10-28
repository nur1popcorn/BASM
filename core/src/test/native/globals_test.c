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
        char **v = str_split(empty_text, ',', &c);
        assert(c == 0);
        free(v);
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
