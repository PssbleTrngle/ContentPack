import { PackLoader, createLogger } from "@pssbletrngle/data-modifier";
import { Mergers } from "@pssbletrngle/resource-merger";

const logger = createLogger();

async function run() {
  const output = new Mergers(
    { output: "../datapacks/generated", overwrite: true },
    {}
  );

  const loader = new PackLoader(logger, { packFormat: 15 });

  loader.content.items.blockItem("example:small_lapis_cog", {
    type: "create:cog",
    block: (blocks) => blocks.cog({ material: "metal", strength: 2.0 }),
  });

  loader.content.items.blockItem("example:large_lapis_cog", {
    type: "create:cog",
    block: (blocks) =>
      blocks.cog({ material: "metal", strength: 2.0, large: true }),
  });

  loader.content.items.blockItem("example:small_ruby_cog", {
    type: "create:cog",
    block: (blocks) => blocks.cog({ copy: "create:cogwheel" }),
  });

  loader.content.items.blockItem("example:large_ruby_cog", {
    type: "create:cog",
    block: (blocks) =>
      blocks.cog({ large: true, copy: "create:large_cogwheel" }),
  });

  logger.info("generating modified resources...");
  await loader.emit(output.createAcceptor());
  await output.finalize();
}

run().catch((e) => logger.error(e.message));
