#!/bin/bash

workingDirectory = $(pwd)


function newMidish  { #input in the form: p t d c
echo "[ANY BUTTON] to start recording"
								read -rsn1
trap : 2
midish -b -v <<END


	dnew 0 "14:0" ro
	tnew drums
	r
	save "$1-$2-$3-$4" ; export "$1-$2-$3-$4.mid"
END

echo "[ANY BUTTON] to save and continue"
								read -rsn1
	
}
	

function main {
	echo "Name of the performer:"
	read performer
	
	echo "Dataset:$dataset"
	
	for tab in `seq 0 9`
	do
		echo "Starting tab $tab"
		for dataset in `seq 0 3`
		do
			echo "Recording dataset:$dataset"
			if [ "$dataset" -eq 1 ]
				then
					for count in `seq 0 9`
					do
						echo "Count:$count"
						newMidish $performer $tab $dataset $count
					done
			else
			newMidish $performer $tab $dataset 0
			fi
			echo "[ANY BUTTON] to go to next dataset"
								read -rsn1
		done
		echo "[ANY BUTTON] to go to next dataset"
								read -rsn1
	done

}

main;
echo "DONE"
