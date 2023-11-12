package com.possible_triangle.content_packs.loader;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.util.Unit;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ContentLoader  {

    private static final CompletableFuture<Unit> INITIAL_TASK = CompletableFuture.completedFuture(Unit.INSTANCE);

    private static Stream<RepositorySource> createDefaultSources(File directory) {
        return Stream.of(new FolderRepositorySource(directory, PackSource.DEFAULT));
    }

    private final Executor executor;
    private final ReloadableResourceManager manager = new ReloadableResourceManager(PackType.SERVER_DATA);

    private final PackRepository packRepository;

    public ContentLoader(Executor executor, File directory) {
        this.executor = executor;
        this.packRepository = new PackRepository(this::createPack, createDefaultSources(directory).toArray(RepositorySource[]::new));
    }

    private Pack createPack(String name, Component component, boolean b, Supplier<PackResources> supplier, PackMetadataSection packMetadataSection, Pack.Position position, PackSource packSource) {
        return new Pack(name, component, b, supplier, packMetadataSection, PackType.SERVER_DATA, position, packSource);
    }

    public void register(PreparableReloadListener listener) {
        this.manager.registerReloadListener(listener);
    }

    private List<PackResources> findPacks() {
        packRepository.reload();
        var available = packRepository.getAvailableIds();
        packRepository.setSelected(available);
        return packRepository.openAllSelected();
    }

    public ReloadInstance load() {
        return this.manager.createReload(Util.backgroundExecutor(), this.executor, INITIAL_TASK, this.findPacks());
    }

}