echo "seed database..."
curl -i localhost:8080/init

echo "get artists"
curl -i localhost:8080/artist/

echo -e "\nremove a song from album"
curl -i -X DELETE localhost:8080/album_song/1/1
echo -e "get album"
curl -i localhost:8080/album/1
