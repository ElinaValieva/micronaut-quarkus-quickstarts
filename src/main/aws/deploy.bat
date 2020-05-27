@ECHO OFF

if "%OS%"=="Windows_NT" setlocal
set S3_BUCKET_NAME=bucket_name
set ROLE_NAME=role_name

docker build . -t micronaut-quickstart
mkdir -p build
docker run --rm --entrypoint cat micronaut-quickstart  /home/application/function.zip > build/function.zip

set ROLE_ARN=`aws iam get-role --role-name "%ROLE_NAME%" | findstr Arn
if "%ROLE_ARN%" == "" (
    echo "No role %ROLE_NAME% exists. "Create one using: "
    echo "> aws iam create-role --role-name ${ROLE_NAME} --assume-role-policy-document file://lambda-role-policy.json"
    echo "> aws iam attach-role-policy --role-name ${ROLE_NAME} --policy-arn arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
    exit 1
)

set BUCKET=aws s3api head-bucket --bucket "%S3_BUCKET_NAME%" 2>&1 | findstr "Not Found"
if  "%BUCKET%" == "" (
    echo "Bucket not found, create it using: > aws s3 mb s3://${S3_BUCKET_NAME}"
    exit 1
)

aws cloudformation package --template-file sam.yaml --output-template-file packaged-sam.yaml --s3-bucket ${S3_BUCKET_NAME}
aws cloudformation deploy --template-file ./packaged-sam.yaml --stack-name MicronautGraalVmDemo --capabilities CAPABILITY_IAM
aws cloudformation describe-stacks --stack-name MicronautGraalVmDemo --query "Stacks[0].Outputs[0].OutputValue"| cut -d \" -f 2

if "%OS%"=="Windows_NT" endlocal
