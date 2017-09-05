:: Temp könyvtár létrehozása a bash számára, ha nem létezik
@if not exist "tmp" mkdir tmp

:: Shell script futtatása a kapott paraméterekkel
@tools\bash.exe copyResources.sh %1 %2 %3
