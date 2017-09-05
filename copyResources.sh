#
#   [bash] copyResources.sh file module [logger]
#
#       file:       forrásfile elérési útja az aktuális könyvtárhoz viszonyítva
#       module:     web modul neve, ahová a fájlt be kell mésolni
#       logger:     ha meg van adva, debug üzenet kiírása
#

# Windows alatt a unix-os tool-ok (sed, find, cp) elérhetősége
export PATH=./tools:$PATH

# Unix-kompatibilis fájl elérési út
unixpath=${1//\\//}

# Másolandó fájl relatív elérési útja
dirprefix=`echo $unixpath | sed "s:$2/src/main/webapp/::" | sed "s:[^/]*\.xhtml$::"`;

# Telepített web modulhoz tartozó, szerveren belüli ideiglenes munkakönyvtár elérési útja
targetdir=`find wildfly-9.0.1.Final -type d -name "$2.war"`;

# Másolás, ha megtaláltunk a deployolt modul könyvtárat
if [ "$targetdir" ]; then
    cp $1 $targetdir/$dirprefix
fi

# Debug üzenet, ha meg van adva logger
if [ "$3" ]; then
    echo
    echo "[$3]:" $1 '==>' $targetdir/$dirprefix
fi
