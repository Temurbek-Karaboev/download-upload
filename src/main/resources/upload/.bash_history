docker run --name postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres
which psql
echo $PATH
cd/var
rm /usr/local/var/postgres/postmaster.pid
psql -p 5432 -h localhost
ps -ef | grep postmaster
brew update
sudo netstat -pant | grep postgres
psql -U postgres -c 'SHOW config_file'
sudo updatedb
SHOW config_file;
