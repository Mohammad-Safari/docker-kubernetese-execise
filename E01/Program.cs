using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.HttpLogging;
using Microsoft.Extensions.Logging;

var PROJECT_AUTHOR = "MohammadSafari";
var PROJECT_LOG_DIR = "/var/log/e01";

var getOption = (string argName) => args.SkipWhile(arg => arg != argName).Skip(1).FirstOrDefault();
var portValidator = (string port) => port.All(char.IsDigit) ? port : throw new ArgumentException("Invalid port number: " + port);
var port = getOption("--program-port") ?? Environment.GetEnvironmentVariable("PROGRAM_PORT") ?? "8080";
portValidator(port);

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddRazorPages();
builder.Services.AddW3CLogging(logging => {
    logging.LoggingFields = W3CLoggingFields.All;
    logging.LogDirectory = PROJECT_LOG_DIR;
});

var app = builder.Build();

app.Urls.Add($"http://*:{port}");

app.UseW3CLogging();
app.UseHttpLogging();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");  
}
app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();

app.MapRazorPages();

app.MapGet("/hello", ([FromQuery(Name = "name")] string? name) => $"Hello {name ?? "Stranger"}");
app.MapGet("/author", () => $"Hello {PROJECT_AUTHOR}");

app.Run();
